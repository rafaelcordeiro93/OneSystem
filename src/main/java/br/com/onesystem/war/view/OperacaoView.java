package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.dao.SituacaoFiscalDAO;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.OperacaoBV;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.builder.SituacaoFiscalBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class OperacaoView extends BasicMBImpl<Operacao, OperacaoBV> implements Serializable {

    private Configuracao configuracao;
    private GrupoFiscal grupoFiscalSelecionado;
    private GrupoFiscal grupoFiscalDuplicar;
    private Operacao operacaoDuplicar;
    private SituacaoFiscal situacaoFiscalSelecionada;
    private List<SituacaoFiscal> situacoesFiscais;

    @Inject
    private ConfiguracaoService serviceConfigurcao;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializarConfiguracoes();
    }

    private void inicializarConfiguracoes() {
        try {
            configuracao = serviceConfigurcao.buscar();
            if (configuracao.getMoedaPadrao() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
            }
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void buscaSituacoes() {
        if (e.getId() != null && grupoFiscalSelecionado != null) {
            try {
                situacoesFiscais = new SituacaoFiscalDAO().porOperacao(e.construirComID()).porGrupoFiscal(grupoFiscalSelecionado).ordenadoPorSequencia().listaDeResultados();
            } catch (DadoInvalidoException die) {
                die.print();
            }
        }
    }

    public void adicionaNaSessao() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        try {
            limpaSessao(contexto);

            SessionUtil.put(grupoFiscalSelecionado, "grupoFiscal", contexto);
            SessionUtil.put(e.construirComID(), "operacao", contexto);

            //Busca a sequencia para a situação
            Integer sequencia = 1;
            if (situacoesFiscais != null && !situacoesFiscais.isEmpty()) {
                sequencia = situacoesFiscais.stream().max(Comparator.comparing(SituacaoFiscal::getSequencia)).get().getSequencia() + 1;
            }
            SessionUtil.put(sequencia, "sequencia", contexto);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void limpaSessao(FacesContext contexto) throws FDadoInvalidoException {
        SessionUtil.remove("grupoFiscal", contexto);
        SessionUtil.remove("operacao", contexto);
        SessionUtil.remove("sequencia", contexto);
        SessionUtil.remove("situacaoFiscal", contexto);
    }

    public void adicionaSituacaoNaSessao() {
        try {
            FacesContext contexto = FacesContext.getCurrentInstance();
            if (situacaoFiscalSelecionada != null) {
                limpaSessao(contexto);
                SessionUtil.put(situacaoFiscalSelecionada, "situacaoFiscal", contexto);
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void aumentaPrioridadeDeSituacao() {
        try {
            SituacaoFiscal st = null;
            if (situacaoFiscalSelecionada != null) {
                if (situacaoFiscalSelecionada.getSequencia() != 1) {
                    situacaoFiscalSelecionada.setSequencia(situacaoFiscalSelecionada.getSequencia() - 1);
                    st = situacoesFiscais.get(situacoesFiscais.indexOf(situacaoFiscalSelecionada) - 1);
                    st.setSequencia(st.getSequencia() + 1);
                    new AtualizaDAO<>().atualiza(st);
                    new AtualizaDAO<>().atualiza(situacaoFiscalSelecionada);
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException io) {
            try {
                new AtualizaDAO<>().atualiza(situacaoFiscalSelecionada);
            } catch (DadoInvalidoException die) {
                die.print();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
        ordenaSituacoesFiscais();
    }

    public void diminuiPrioridadeDeSituacao() {
        try {
            SituacaoFiscal st = null;
            if (situacaoFiscalSelecionada != null) {
                if (!situacoesFiscais.stream().max(Comparator.comparing(SituacaoFiscal::getSequencia)).get().getSequencia().equals(situacaoFiscalSelecionada.getSequencia())) {
                    situacaoFiscalSelecionada.setSequencia(situacaoFiscalSelecionada.getSequencia() + 1);
                    st = situacoesFiscais.get(situacoesFiscais.indexOf(situacaoFiscalSelecionada) + 1);
                    st.setSequencia(st.getSequencia() - 1);
                    new AtualizaDAO<>().atualiza(st);
                    new AtualizaDAO<>().atualiza(situacaoFiscalSelecionada);
                }
            }
        } catch (IndexOutOfBoundsException | NullPointerException io) {
            try {
                new AtualizaDAO<>().atualiza(situacaoFiscalSelecionada);
            } catch (DadoInvalidoException die) {
                die.print();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
        ordenaSituacoesFiscais();
    }

    public void duplicarTodasSituacoes() {
        try {
            if (grupoFiscalDuplicar != null && operacaoDuplicar != null) {
                List<SituacaoFiscal> listaDeSituacoesOperacaoDuplicar = new SituacaoFiscalDAO().porOperacao(operacaoDuplicar).porGrupoFiscal(grupoFiscalDuplicar).ordenadoPorSequencia().listaDeResultados();
                Integer sequencia = 1;
                if (listaDeSituacoesOperacaoDuplicar != null && !listaDeSituacoesOperacaoDuplicar.isEmpty()) {
                    sequencia = listaDeSituacoesOperacaoDuplicar.stream().max(Comparator.comparing(SituacaoFiscal::getSequencia)).get().getSequencia() + 1;
                }
                List<SituacaoFiscal> mesmoGrupoEOperacao = new ArrayList<>();
                for (SituacaoFiscal sf : situacoesFiscais) {
                    SituacaoFiscalBV bv = new SituacaoFiscalBV(sf);
                    bv.setOperacao(operacaoDuplicar);
                    bv.setGrupoFiscal(grupoFiscalDuplicar);
                    bv.setSequencia(sequencia);
                    SituacaoFiscal situacaoDuplicada = bv.construir();
                    new AdicionaDAO<>().adiciona(situacaoDuplicada);
                    if (grupoFiscalDuplicar == grupoFiscalSelecionado && operacaoDuplicar.getId().equals(e.getId())) {
                        mesmoGrupoEOperacao.add(situacaoDuplicada);
                    }
                    sequencia++;
                }
                if (!mesmoGrupoEOperacao.isEmpty()) {
                    mesmoGrupoEOperacao.forEach((sf) -> {
                        situacoesFiscais.add(sf);
                    });
                    ordenaSituacoesFiscais();
                }
                operacaoDuplicar = null;
                grupoFiscalDuplicar = null;
                InfoMessage.print(new BundleUtil().getMessage("Situacoes_Duplicadas_Com_Sucesso"));
            } else {
                ErrorMessage.print(new BundleUtil().getMessage("Selecione_Grupo_Fiscal_Operacao_Para_Duplicar_Situacoes"));
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void duplicarSituacao() {
        try {
            if (situacaoFiscalSelecionada != null) {
                SituacaoFiscal situacaoDuplicada = new SituacaoFiscalBV(situacaoFiscalSelecionada).construir();
                Integer sequencia = 1;
                if (situacoesFiscais != null && !situacoesFiscais.isEmpty()) {
                    sequencia = situacoesFiscais.stream().max(Comparator.comparing(SituacaoFiscal::getSequencia)).get().getSequencia() + 1;
                }
                situacaoDuplicada.setSequencia(sequencia);
                new AdicionaDAO<>().adiciona(situacaoDuplicada);
                situacoesFiscais.add(situacaoDuplicada);
                ordenaSituacoesFiscais();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void atualizaSituacoesFiscais(SelectEvent event) {
        SituacaoFiscal registro = (SituacaoFiscal) event.getObject();
        boolean encontrou = false;
        for (SituacaoFiscal s : situacoesFiscais) {
            if (s.getId().equals(registro.getId())) {
                encontrou = true;
                situacoesFiscais.set(situacoesFiscais.indexOf(s), registro);
                break;
            }
        }
        if (!encontrou) {
            situacoesFiscais.add(registro);
        }
        ordenaSituacoesFiscais();
    }

    private void ordenaSituacoesFiscais() {
        situacoesFiscais.sort(Comparator.comparing(SituacaoFiscal::getSequencia));
    }

    public void removeSituacao() {
        try {
            if (situacaoFiscalSelecionada != null) {
                new RemoveDAO<>().remove(situacaoFiscalSelecionada, situacaoFiscalSelecionada.getId());
                situacoesFiscais.remove(situacaoFiscalSelecionada);
                ordenaSituacoesFiscais();
                for (int i = 0; i < situacoesFiscais.size(); i++) {
                    if (!situacoesFiscais.get(i).getSequencia().equals(i + 1)) {
                        situacoesFiscais.get(i).setSequencia(i + 1);
                        new AtualizaDAO<>().atualiza(situacoesFiscais.get(i));
                    }
                }
                ordenaSituacoesFiscais();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void limparJanela() {
        e = new OperacaoBV();
        grupoFiscalSelecionado = null;
        situacaoFiscalSelecionada = null;
        operacaoDuplicar = null;
        grupoFiscalDuplicar = null;
        situacoesFiscais = new ArrayList<>();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof Operacao && "operacaoId-search".equals(idComponent)) {
            t = (Operacao) obj;
            e = new OperacaoBV(t);
            grupoFiscalSelecionado = null;
            situacaoFiscalSelecionada = null;
            operacaoDuplicar = null;
            grupoFiscalDuplicar = null;
            situacoesFiscais = new ArrayList<>();
        } else if (obj instanceof TipoReceita && "receitaAVista-search".equals(idComponent)) {
            e.setVendaAVista((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "receitaAPrazo-search".equals(idComponent)) {
            e.setVendaAPrazo((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "servicoAVista-search".equals(idComponent)) {
            e.setServicoAVista((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "servicoAPrazo-search".equals(idComponent)) {
            e.setServicoAPrazo((TipoReceita) obj);
        } else if (obj instanceof TipoReceita && "receitaFrete-search".equals(idComponent)) {
            e.setReceitaFrete((TipoReceita) obj);
        } else if (obj instanceof TipoDespesa && "despesaCMV-search".equals(idComponent)) {
            e.setDespesaCMV((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && "despesaAVista-search".equals(idComponent)) {
            e.setCompraAVista((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && "despesaAPrazo-search".equals(idComponent)) {
            e.setCompraAPrazo((TipoDespesa) obj);
        } else if (obj instanceof LoteNotaFiscal) {
            e.setLoteNotaFiscal((LoteNotaFiscal) obj);
        } else if (obj instanceof GrupoFiscal) {
            grupoFiscalDuplicar = (GrupoFiscal) obj;
        } else if (obj instanceof Operacao) {
            operacaoDuplicar = (Operacao) obj;
        }
    }

    public List<OperacaoFinanceira> getOperacaoFinanceira() {
        return Arrays.asList(OperacaoFinanceira.values());
    }

    public List<TipoLancamento> getTipoNota() {
        return Arrays.asList(TipoLancamento.values());
    }

    public List<TipoOperacao> getTipoDeOperacao() {
        return Arrays.asList(TipoOperacao.values());
    }

    public List<TipoContabil> getTipoContabil() {
        return Arrays.asList(TipoContabil.values());
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
    }

    public GrupoFiscal getGrupoFiscalSelecionado() {
        return grupoFiscalSelecionado;
    }

    public void setGrupoFiscalSelecionado(GrupoFiscal grupoFiscalSelecionado) {
        this.grupoFiscalSelecionado = grupoFiscalSelecionado;
    }

    public SituacaoFiscal getSituacaoFiscalSelecionada() {
        return situacaoFiscalSelecionada;
    }

    public void setSituacaoFiscalSelecionada(SituacaoFiscal situacaoFiscalSelecionada) {
        this.situacaoFiscalSelecionada = situacaoFiscalSelecionada;
    }

    public List<SituacaoFiscal> getSituacoesFiscais() {
        return situacoesFiscais;
    }

    public void setSituacoesFiscais(List<SituacaoFiscal> situacoesFiscais) {
        this.situacoesFiscais = situacoesFiscais;
    }

    public GrupoFiscal getGrupoFiscalDuplicar() {
        return grupoFiscalDuplicar;
    }

    public void setGrupoFiscalDuplicar(GrupoFiscal grupoFiscalDuplicar) {
        this.grupoFiscalDuplicar = grupoFiscalDuplicar;
    }

    public Operacao getOperacaoDuplicar() {
        return operacaoDuplicar;
    }

    public void setOperacaoDuplicar(Operacao operacaoDuplicar) {
        this.operacaoDuplicar = operacaoDuplicar;
    }

}
