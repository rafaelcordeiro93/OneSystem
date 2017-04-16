package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.ReceitaProvisionadaDAO;
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.ResumoDeMoeda;
import br.com.onesystem.services.RelatorioContaAbertaComparator;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.valueobjects.EstadoConta;
import br.com.onesystem.valueobjects.TipoBusca;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.builder.RelatorioDeContaAReceberBV;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeContaAReceberView implements Serializable {

    private RelatorioDeContaAReceberBV relatorio;
    private BaixaDAO baixaDAO;
    private TituloDAO tituloDAO;
    private String nomeRelatorio;
    private ReceitaProvisionadaDAO receitaProvisionadaDao;
    private ImpressoraDeRelatorio impressora;

    @PostConstruct
    public void init() {
        relatorio = new RelatorioDeContaAReceberBV();
        baixaDAO = new BaixaDAO();
        tituloDAO = new TituloDAO();
        impressora = new ImpressoraDeRelatorio();
        receitaProvisionadaDao = new ReceitaProvisionadaDAO();
    }

    public void imprimir() {
        try {
            validarDadosDeEntrada();
            List<?> lista;
            if (relatorio.getEstadoConta() == EstadoConta.RECEBIDO) {
                lista = buscarDadosPagos();
                if (lista.isEmpty()) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
                }
            } else {
                lista = buscarDadosAbertos();
                if (lista.isEmpty() || (relatorio.getTipo() == TipoOperacao.RECEITA)) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
                }
            }
            Map<String, Object> totais = criarListaDeTotais(lista);
            impressora.comParametros(totais).imprimir(lista, nomeRelatorio);

        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private Map<String, Object> criarListaDeTotais(List<?> lista) {
        List<Moeda> listaMoeda = new ArmazemDeRegistros<Moeda>(Moeda.class).listaTodosOsRegistros();
        List<ResumoDeMoeda> resumo = new ArrayList<ResumoDeMoeda>();

        for (Moeda moeda : listaMoeda) {
            BigDecimal saldo = new BigDecimal(0);
            BigDecimal valor = new BigDecimal(0);
            BigDecimal valorBaixado = new BigDecimal(0);
            boolean passou = false;
            for (Object object : lista) {
                if (object instanceof Baixa) {
                    Baixa b = (Baixa) object;
                    if (moeda.getId().equals(b.getCotacao().getConta().getMoeda().getId())) {
                        valor = valor.add(b.getValor());
                        passou = true;
                    }
                } else {
                    RelatorioContaAbertaImpl b = (RelatorioContaAbertaImpl) object;
                    if (moeda.getId().equals(b.getMoeda().getId())) {
                        valor = valor.add(b.getValor());
                        saldo = saldo.add(b.getSaldo());
                        valorBaixado = valorBaixado.add(b.getValorBaixado());
                        passou = true;
                    }
                }
            }
            if (passou) {
                resumo.add(new ResumoDeMoeda(moeda, valor, saldo, valorBaixado));
            }
        }

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("LISTA_RESUMO_MOEDA", resumo);

        return parametros;
    }

    private List<?> buscarDadosAbertos() throws EDadoInvalidoException {
        nomeRelatorio = "RelatorioDeContasAReceberAbertas";
        if (relatorio.getTipo() == TipoOperacao.TITULO) {
            return buscarTitulosAbertos();
        } else if (relatorio.getTipo() == TipoOperacao.RECEITA_PROVISIONADA) {
            return buscaReceitasProvisionadasAReceber();
        } else {
            return buscarTodasContasAPagar();
        }
    }

    private List<RelatorioContaAbertaImpl> buscarTodasContasAPagar() throws EDadoInvalidoException {
        List<RelatorioContaAbertaImpl> lista = new ArrayList<RelatorioContaAbertaImpl>();
        for (Titulo titulo : buscarTitulosAbertos()) {
            lista.add(titulo);
        }
        for (ReceitaProvisionada despesa : buscaReceitasProvisionadasAReceber()) {
            lista.add(despesa);
        }
        RelatorioContaAbertaComparator comparator = new RelatorioContaAbertaComparator();
        Collections.sort(lista, comparator);
        return lista;
    }

    private List<ReceitaProvisionada> buscaReceitasProvisionadasAReceber() throws EDadoInvalidoException {
        List<ReceitaProvisionada> lista;
        if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
            lista = receitaProvisionadaDao.buscarReceitasProvisionadas().wAReceber()
                    .ePorEmissao(relatorio.getDataInicial(), relatorio.getDataFinal())
                    .ePorPessoa(relatorio.getPessoa()).orderByMoeda().gerarDados();
        } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
            lista = receitaProvisionadaDao.buscarReceitasProvisionadas().wAReceber()
                    .ePorVencimento(relatorio.getDataInicial(), relatorio.getDataFinal())
                    .ePorPessoa(relatorio.getPessoa()).orderByMoeda().gerarDados();
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
        }
        return lista;
    }

    private List<Titulo> buscarTitulosAbertos() throws EDadoInvalidoException {
        List<Titulo> lista;
        if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
            lista = tituloDAO.buscarTitulos().wAReceber().eAbertas().ePorEmissao(relatorio.getDataInicial(), relatorio.getDataFinal()).ePorPessoa(relatorio.getPessoa()).orderByMoeda().listaDeResultados();
        } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
            lista = tituloDAO.buscarTitulos().wAReceber().eAbertas().ePorVencimento(relatorio.getDataInicial(), relatorio.getDataFinal()).ePorPessoa(relatorio.getPessoa()).orderByMoeda().listaDeResultados();
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
        }
        return lista;
    }

    private List<Baixa> buscarDadosPagos() {
        List<Baixa> lista;
        nomeRelatorio = "RelatorioDeContasAReceberRecebidas";
        if (relatorio.getTipo() == TipoOperacao.RECEITA) {
            lista = baixaDAO.buscarBaixasW().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                    .ePorConta(relatorio.getConta()).ePorPessoa(relatorio.getPessoa()).eEntrada().eSemReceitaProvisionada()
                    .eSemTitulo().eNaoCancelada().orderByMoeda().listaDeResultados();
        } else if (relatorio.getTipo() == TipoOperacao.TITULO) {
            if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
                lista = baixaDAO.buscarBaixasW().eComTitulo().eComTituloPagoRecebido().eEntrada().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
                lista = baixaDAO.buscarBaixasW().eComTitulo().eEntrada().eComTituloPagoRecebido().ePorVencimentoDeTituloEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else {
                lista = baixaDAO.buscarBaixasW().eComTitulo().eComTituloPagoRecebido().eEntrada().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            }
        } else if (relatorio.getTipo() == TipoOperacao.RECEITA_PROVISIONADA) {
            if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
                lista = baixaDAO.buscarBaixasW().eComReceitaProvisionada().eEntrada().ePorEmissaoDaReceitaProvisionadaEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorConta(relatorio.getConta()).ePorPessoa(relatorio.getPessoa()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
                lista = baixaDAO.buscarBaixasW().eComReceitaProvisionada().ePorVencimentoDeDespesaProvisionadaEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .eEntrada().ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else {
                lista = baixaDAO.buscarBaixasW().eComReceitaProvisionada().eEntrada().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            }
        } else {
            lista = baixaDAO.buscarBaixasW().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                    .ePorConta(relatorio.getConta()).ePorPessoa(relatorio.getPessoa()).eEntrada().eNaoCancelada().orderByMoeda().listaDeResultados();
        }
        return lista;
    }

    public List<EstadoConta> getEstadoConta() {
        return Arrays.asList(EstadoConta.ABERTO, EstadoConta.RECEBIDO);
    }

    public List<TipoOperacao> getTipoOperacao() {
        return Arrays.asList(TipoOperacao.TITULO, TipoOperacao.RECEITA, TipoOperacao.RECEITA_PROVISIONADA, TipoOperacao.TODOS);
    }

    private void validarDadosDeEntrada() throws EDadoInvalidoException {
        if (relatorio.getDataInicial() == null || relatorio.getDataFinal() == null) {
            throw new EDadoInvalidoException("As datas devem ser informadas!");
        }

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(relatorio.getDataInicial() != null ? relatorio.getDataInicial() : Calendar.getInstance().getTime());
        dataAtual.set(Calendar.HOUR_OF_DAY, 0);
        dataAtual.set(Calendar.MINUTE, 0);
        dataAtual.set(Calendar.SECOND, 0);
        relatorio.setDataInicial(dataAtual.getTime());

        Calendar dataFinal = Calendar.getInstance();
        dataFinal.setTime(relatorio.getDataFinal() != null ? relatorio.getDataFinal() : Calendar.getInstance().getTime());
        dataFinal.set(Calendar.HOUR_OF_DAY, 23);
        dataFinal.set(Calendar.MINUTE, 59);
        dataFinal.set(Calendar.SECOND, 59);
        relatorio.setDataFinal(dataFinal.getTime());
    }

    public List<TipoBusca> getTipoBusca() {
        return Arrays.asList(TipoBusca.EMISSAO, TipoBusca.VENCIMENTO, TipoBusca.RECEBIMENTO);
    }

    public void selecionaConta(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        relatorio.setConta(conta);
    }

    public void removerConta() {
        relatorio.setConta(null);
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        relatorio.setPessoa(pessoa);
    }

    public void removerPessoa() {
        relatorio.setPessoa(null);
    }

    public RelatorioDeContaAReceberBV getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioDeContaAReceberBV relatorio) {
        this.relatorio = relatorio;
    }

}
