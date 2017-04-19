package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.OperacaoDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.OperacaoBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class OperacaoView implements Serializable {

    private OperacaoBV operacao;
    private Operacao operacaoSelecionada;
    private Configuracao configuracao;

    @ManagedProperty("#{configuracaoService}")
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

    public void add() {
        try {
            Operacao novoRegistro = operacao.construir();
            new AdicionaDAO<Operacao>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {

            if (operacaoSelecionada != null) {
                Operacao operacaoExistente = operacao.construirComID();
                new AtualizaDAO<Operacao>().atualiza(operacaoExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("ajuste_estoque_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (operacaoSelecionada != null) {
                new RemoveDAO<Operacao>().remove(operacaoSelecionada, operacaoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaReceitaAVista(SelectEvent event) {
        Receita receitaSelecionada = (Receita) event.getObject();
        this.operacao.setVendaAVista(receitaSelecionada);
    }

    public void selecionaReceitaAPrazo(SelectEvent event) {
        Receita receitaSelecionada = (Receita) event.getObject();
        this.operacao.setVendaAPrazo(receitaSelecionada);
    }

    public void selecionaServicoAVista(SelectEvent event) {
        Receita receitaSelecionada = (Receita) event.getObject();
        this.operacao.setServicoAVista(receitaSelecionada);
    }

    public void selecionaServicoAPrazo(SelectEvent event) {
        Receita receitaSelecionada = (Receita) event.getObject();
        this.operacao.setServicoAPrazo(receitaSelecionada);
    }

    public void selecionaReceitaFrete(SelectEvent event) {
        Receita receitaSelecionada = (Receita) event.getObject();
        this.operacao.setReceitaFrete(receitaSelecionada);
    }

    public void selecionaDespesaCMV(SelectEvent event) {
        TipoDespesa despesaSelecionada = (TipoDespesa) event.getObject();
        this.operacao.setDespesaCMV(despesaSelecionada);
    }

    public void selecionaCompraAVista(SelectEvent event) {
        TipoDespesa despesaSelecionada = (TipoDespesa) event.getObject();
        this.operacao.setCompraAVista(despesaSelecionada);
    }

    public void selecionaCompraAPrazo(SelectEvent event) {
        TipoDespesa despesaSelecionada = (TipoDespesa) event.getObject();
        this.operacao.setCompraAPrazo(despesaSelecionada);
    }

    public void selecionaOperacao(SelectEvent e) {
        Operacao a = (Operacao) e.getObject();
        operacao = new OperacaoBV(a);
        operacaoSelecionada = a;
    }

    public void buscaPorId() {
        Long id = operacao.getId();
        if (id != null) {
            try {
                OperacaoDAO dao = new OperacaoDAO();
                Operacao c = dao.buscarOperacao().porId(id).resultado();
                operacaoSelecionada = c;
                operacao = new OperacaoBV(operacaoSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                operacao.setId(id);
                die.print();
            }
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

    public void limparJanela() {
        operacao = new OperacaoBV();
        operacaoSelecionada = null;
    }

    public void desfazer() {
        if (operacaoSelecionada != null) {
            operacao = new OperacaoBV(operacaoSelecionada);
        }
    }

    public OperacaoBV getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoBV operacao) {
        this.operacao = operacao;
    }

    public Operacao getOperacaoSelecionada() {
        return operacaoSelecionada;
    }

    public void setOperacaoSelecionada(Operacao operacaoSelecionada) {
        this.operacaoSelecionada = operacaoSelecionada;
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
}
