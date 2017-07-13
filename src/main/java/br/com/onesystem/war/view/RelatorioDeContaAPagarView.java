package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.DespesaProvisionadaDAO;
import br.com.onesystem.dao.TituloDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
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
import br.com.onesystem.war.builder.RelatorioDeContaAPagarBV;
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
public class RelatorioDeContaAPagarView implements Serializable {

    private RelatorioDeContaAPagarBV relatorio;
    private BaixaDAO baixaDAO;
    private TituloDAO tituloDAO;
    private DespesaProvisionadaDAO despesaProvisionadaDao;
    private String nomeRelatorio;

    @PostConstruct
    public void init() {
        baixaDAO = new BaixaDAO();
        tituloDAO = new TituloDAO();
        relatorio = new RelatorioDeContaAPagarBV();
        despesaProvisionadaDao = new DespesaProvisionadaDAO();
    }

    public void imprimir() {
        try {
            validarDadosDeEntrada();
            List<?> lista;
            if (relatorio.getEstadoConta() == EstadoConta.PAGO) {
                lista = buscarDadosPagos();
                if (lista.isEmpty()) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
                }
            } else {
                lista = buscarDadosAbertos();
                if (lista.isEmpty() || (relatorio.getTipo() == TipoOperacao.DESPESA)) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
                }
            }
            Map<String, Object> totais = criarListaDeTotais(lista);
            ImpressoraDeRelatorio impressora = new ImpressoraDeRelatorio();
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
        nomeRelatorio = "RelatorioDeContasAPagarAbertas";
        if (relatorio.getTipo() == TipoOperacao.TITULO) {
            return buscaTitulosAPagar();
        } else if (relatorio.getTipo() == TipoOperacao.DESPESA_PROVISIONADA) {
            return buscaDespesasProvisionadasAPagar();
        } else {
            return buscarTodasContasAPagar();
        }
    }

    private List<RelatorioContaAbertaImpl> buscarTodasContasAPagar() throws EDadoInvalidoException {
        List<RelatorioContaAbertaImpl> lista = new ArrayList<RelatorioContaAbertaImpl>();
        for (Titulo titulo : buscaTitulosAPagar()) {
            lista.add(titulo);
        }
        for (DespesaProvisionada despesa : buscaDespesasProvisionadasAPagar()) {
            lista.add(despesa);
        }
        RelatorioContaAbertaComparator comparator = new RelatorioContaAbertaComparator();
        Collections.sort(lista, comparator);
        return lista;
    }

    private List<DespesaProvisionada> buscaDespesasProvisionadasAPagar() throws EDadoInvalidoException {
        List<DespesaProvisionada> lista;
        if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
            lista = despesaProvisionadaDao.buscarDespesasProvisionadas().wAPagar()
                    .ePorEmissao(relatorio.getDataInicial(), relatorio.getDataFinal())
                    .ePorPessoa(relatorio.getPessoa()).orderByMoeda().gerarDados();
        } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
            lista = despesaProvisionadaDao.buscarDespesasProvisionadas().wAPagar()
                    .ePorVencimento(relatorio.getDataInicial(), relatorio.getDataFinal())
                    .ePorPessoa(relatorio.getPessoa()).orderByMoeda().gerarDados();
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
        }
        return lista;
    }

    private List<Titulo> buscaTitulosAPagar() throws EDadoInvalidoException {
        List<Titulo> lista;
        if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
            lista = tituloDAO.aPagar().eAbertas().ePorEmissao(relatorio.getDataInicial(), relatorio.getDataFinal()).ePorPessoa(relatorio.getPessoa()).orderByMoeda().listaDeResultados();
        } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
            lista = tituloDAO.aPagar().eAbertas().ePorVencimento(relatorio.getDataInicial(), relatorio.getDataFinal()).ePorPessoa(relatorio.getPessoa()).orderByMoeda().listaDeResultados();
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Nao_existem_dados"));
        }
        return lista;
    }

    private List<Baixa> buscarDadosPagos() {
        List<Baixa> lista;
        if (relatorio.getTipo() == TipoOperacao.DESPESA) {
            lista = baixaDAO.buscarBaixasW().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal()).ePorConta(relatorio.getConta()).ePorPessoa(relatorio.getPessoa())
                    .eSaida().eSemTitulo().eSemDespesaProvisionada().eNaoCancelada().orderByMoeda().listaDeResultados();
        } else if (relatorio.getTipo() == TipoOperacao.TITULO) {
            if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
                lista = baixaDAO.buscarBaixasW().eComTitulo().eComTituloPagoRecebido().eSaida().ePorEmissaoDoTituloEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
                lista = baixaDAO.buscarBaixasW().eComTitulo().eComTituloPagoRecebido().eSaida().ePorVencimentoDeTituloEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else {
                lista = baixaDAO.buscarBaixasW().eComTitulo().eComTituloPagoRecebido().eSaida().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            }
        } else if (relatorio.getTipo() == TipoOperacao.DESPESA_PROVISIONADA) {
            if (relatorio.getTipoBusca() == TipoBusca.EMISSAO) {
                lista = baixaDAO.buscarBaixasW().eComDespesaProvisionada().eSaida().ePorEmissaoDaDespesaProvisionadaEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorConta(relatorio.getConta()).ePorPessoa(relatorio.getPessoa()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else if (relatorio.getTipoBusca() == TipoBusca.VENCIMENTO) {
                lista = baixaDAO.buscarBaixasW().eComDespesaProvisionada().ePorVencimentoDeDespesaProvisionadaEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .eSaida().ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            } else {
                lista = baixaDAO.buscarBaixasW().eComDespesaProvisionada().eSaida().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal())
                        .ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
            }
        } else {
            lista = baixaDAO.buscarBaixasW().ePorEmissaoEntre(relatorio.getDataInicial(), relatorio.getDataFinal()).eSaida().ePorPessoa(relatorio.getPessoa()).ePorConta(relatorio.getConta()).eNaoCancelada().orderByMoeda().listaDeResultados();
        }
        nomeRelatorio = "RelatorioDeContasAPagarPagas";
        return lista;
    }

    public List<EstadoConta> getEstadoConta() {
        return Arrays.asList(EstadoConta.ABERTO, EstadoConta.PAGO);
    }

    public List<TipoOperacao> getTipoOperacao() {
        return Arrays.asList(TipoOperacao.TITULO, TipoOperacao.DESPESA, TipoOperacao.DESPESA_PROVISIONADA, TipoOperacao.TODOS);
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
        return Arrays.asList(TipoBusca.EMISSAO, TipoBusca.VENCIMENTO, TipoBusca.PAGAMENTO);
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

    public RelatorioDeContaAPagarBV getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioDeContaAPagarBV relatorio) {
        this.relatorio = relatorio;
    }

}
