package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.DespesaEventual;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ReceitaEventual;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.ModalidadeDeCobrancaFixa;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.CreditoBV;
import br.com.onesystem.war.builder.DespesaEventualBV;
import br.com.onesystem.war.builder.ReceitaEventualBV;
import br.com.onesystem.war.builder.TipoDeCobrancaBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CreditoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoTipoDeCobrancaView extends BasicMBImpl<TipoDeCobranca, TipoDeCobrancaBV> implements Serializable {

    private NaturezaFinanceira recebimentoOuPagamento;
    private CreditoBV credito;
    private ReceitaEventualBV receitaEventualBV;
    private DespesaEventualBV despesaEventualBV;
    private Titulo titulo;
    private BoletoDeCartao boletoDeCartao;
    private Cheque cheque;
    private Date emissao;
    private DespesaProvisionada despesaProvisionada;
    private ReceitaProvisionada receitaProvisionada;
    private ModalidadeDeCobranca modalidadeDeCobranca;
    private ModalidadeDeCobrancaFixa modalidadeDeCobrancaFixa;
    private Model<TipoDeCobranca> model;
    private List<Cotacao> cotacaoLista;
    private List<Conta> contaComCotacao;

    @Inject
    private ConfiguracaoService serviceConf;

    @Inject
    private CreditoService creditoService;
    private Cotacao cotacaoPadrao;

    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
            inicializar();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void limparJanela() {
        t = null;
        e = new TipoDeCobrancaBV();
        credito = new CreditoBV();
        receitaEventualBV = new ReceitaEventualBV();
        despesaEventualBV = new DespesaEventualBV();
    }

    private void buscaDaSessao() throws DadoInvalidoException {
        recebimentoOuPagamento = (NaturezaFinanceira) SessionUtil.getObject("naturezaFinanceira", FacesContext.getCurrentInstance());
        modalidadeDeCobranca = (ModalidadeDeCobranca) SessionUtil.getObject("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        modalidadeDeCobrancaFixa = (ModalidadeDeCobrancaFixa) SessionUtil.getObject("modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        if (modalidadeDeCobranca != null || modalidadeDeCobrancaFixa != null) {
            emissao = (Date) SessionUtil.getObject("emissao", FacesContext.getCurrentInstance());
            model = (Model<TipoDeCobranca>) SessionUtil.getObject("modelTipo", FacesContext.getCurrentInstance());
            if (model != null) {
                t = (TipoDeCobranca) model.getObject();
                e = new TipoDeCobrancaBV(model.getObject());
            }
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Modalidade_Cobranca_Deve_Ser_Informada"));
        }
    }

    private void inicializar() throws DadoInvalidoException {
        if (model != null) {
            if (model.getObject().getCobranca() != null) {
                if (model.getObject().getCobranca() instanceof Titulo) {
                    titulo = (Titulo) model.getObject().getCobranca();
                    modalidadeDeCobranca = ModalidadeDeCobranca.TITULO;
                    modalidadeDeCobrancaFixa = null;
                } else if (model.getObject().getCobranca() instanceof BoletoDeCartao) {
                    boletoDeCartao = (BoletoDeCartao) model.getObject().getCobranca();
                    modalidadeDeCobranca = ModalidadeDeCobranca.CARTAO;
                    modalidadeDeCobrancaFixa = null;
                } else if (model.getObject().getCobranca() instanceof Credito) {
                    credito = new CreditoBV((Credito) model.getObject().getCobranca());
                    modalidadeDeCobranca = ModalidadeDeCobranca.CREDITO;
                    modalidadeDeCobrancaFixa = null;
                } else {
                    cheque = (Cheque) model.getObject().getCobranca();
                    modalidadeDeCobranca = ModalidadeDeCobranca.CHEQUE;
                    modalidadeDeCobrancaFixa = null;
                }
            } else {
                if (model.getObject().getCobrancaFixa() instanceof ReceitaEventual) {
                    receitaEventualBV = new ReceitaEventualBV((ReceitaEventual) model.getObject().getCobrancaFixa());
                    modalidadeDeCobrancaFixa = ModalidadeDeCobrancaFixa.RECEITA_EVENTUAL;
                    modalidadeDeCobranca = null;
                } else if (model.getObject().getCobrancaFixa() instanceof ReceitaProvisionada) {
                    receitaProvisionada = (ReceitaProvisionada) model.getObject().getCobrancaFixa();
                    modalidadeDeCobrancaFixa = ModalidadeDeCobrancaFixa.RECEITA_PROVISIONADA;
                    modalidadeDeCobranca = null;
                } else if (model.getObject().getCobrancaFixa() instanceof DespesaEventual) {
                    despesaEventualBV = new DespesaEventualBV((DespesaEventual) model.getObject().getCobrancaFixa());
                    modalidadeDeCobrancaFixa = ModalidadeDeCobrancaFixa.DESPESA_EVENTUAL;
                    modalidadeDeCobranca = null;
                } else {
                    despesaProvisionada = (DespesaProvisionada) model.getObject().getCobrancaFixa();
                    modalidadeDeCobrancaFixa = ModalidadeDeCobrancaFixa.DESPESA_PROVISIONADA;
                    modalidadeDeCobranca = null;
                }
            }
        }

        cotacaoLista = new CotacaoDAO().buscarCotacoes().naEmissao(emissao).porCotacaoEmpresa().listaDeResultados();
        contaComCotacao = new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
        cotacaoPadrao = new CotacaoDAO().buscarCotacoes().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(emissao).porCotacaoEmpresa().resultado();
        e.setCotacao(cotacaoPadrao);
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 670);
        opcoes.put("draggable", true);
        opcoes.put("height", 440);
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoTipoDeCobranca", opcoes, null);
    }

    public List<ModalidadeDeCobranca> getModalidadesDeCobranca() {
        return Arrays.asList(ModalidadeDeCobranca.values());
    }

    public void selecionaCobrancaNoObjeto() {
        Cobranca c = e.getCobranca();
        if (c instanceof Titulo) {
            titulo = (Titulo) c;
        } else if (c instanceof Cheque) {
            cheque = (Cheque) c;
            e.setValor(cheque.getValor());
            e.setCotacao(cheque.getCotacao());
            e.setConta(cheque.getCotacao().getConta());
        } else if (c instanceof BoletoDeCartao) {
            boletoDeCartao = (BoletoDeCartao) c;
            e.setValor(boletoDeCartao.getValor());
            e.setCotacao(boletoDeCartao.getCotacao());
            e.setConta(boletoDeCartao.getCotacao().getConta());
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        String id = event.getComponent().getId();
        if (obj instanceof Cobranca) {
            e.setCobranca((Cobranca) event.getObject());
            selecionaCobrancaNoObjeto();
        } else if (obj instanceof Pessoa && id.equals("inp-Credito-search")) {
            credito.setPessoa((Pessoa) obj);
        } else if (obj instanceof Pessoa && id.equals("inp-despesaPessoa-search")) {
            despesaEventualBV.setPessoa((Pessoa) obj);
        } else if (obj instanceof Pessoa && id.equals("inp-receitaPessoa-search")) {
            receitaEventualBV.setPessoa((Pessoa) obj);
        } else if (obj instanceof TipoDespesa) {
            despesaEventualBV.setDespesa((TipoDespesa) obj);
        } else if (obj instanceof TipoReceita) {
            receitaEventualBV.setReceita((TipoReceita) obj);
        } else if (obj instanceof DespesaProvisionada) {
            despesaProvisionada = (DespesaProvisionada) obj;
        } else if (obj instanceof ReceitaProvisionada) {
            receitaProvisionada = (ReceitaProvisionada) obj;
        }
    }

    public void selecionaCotacaoConformeConta() {
        try {
            if (e.getConta() != null) {
                e.setCotacao(new CotacaoDAO().buscarCotacoes().porConta(e.getConta()).naUltimaEmissao(emissao).resultado());
            } else {
                e.setCotacao(cotacaoPadrao);
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void salvar() {
        try {
            removeDaSessao();
            construir();
            if (model != null) {
                model.setObject(e.construirComID());
                RequestContext.getCurrentInstance().closeDialog(model);
            } else {
                RequestContext.getCurrentInstance().closeDialog(e.construirComID());
            }
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void construir() throws DadoInvalidoException {
        if (modalidadeDeCobranca == ModalidadeDeCobranca.CARTAO) {
            boletoDeCartao.quita();
        } else if (modalidadeDeCobranca == ModalidadeDeCobranca.CHEQUE) {
            cheque.desconta();
        } else if (modalidadeDeCobranca == ModalidadeDeCobranca.CREDITO) {
            credito.setValor(e.getValor());
            credito.setEmissao(emissao);
            credito.setVencimento(emissao);
            credito.setCotacao(e.getCotacao());
            credito.setHistorico(e.getObservacao());
            if (recebimentoOuPagamento == NaturezaFinanceira.RECEITA) {
                credito.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
            } else if (recebimentoOuPagamento == NaturezaFinanceira.DESPESA) {
                credito.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
            }
            e.setCobranca(credito.construirComID());
        } else if (modalidadeDeCobrancaFixa == ModalidadeDeCobrancaFixa.RECEITA_EVENTUAL) {
            receitaEventualBV.setCotacao(e.getCotacao());
            receitaEventualBV.setValor(e.getValor());
            receitaEventualBV.setEmissao(emissao);
            receitaEventualBV.setVencimento(emissao);
            receitaEventualBV.setHistorico(e.getObservacao());
            receitaEventualBV.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
            e.setCobrancaFixa(receitaEventualBV.construirComID());
        } else if (modalidadeDeCobrancaFixa == ModalidadeDeCobrancaFixa.DESPESA_EVENTUAL) {
            despesaEventualBV.setCotacao(e.getCotacao());
            despesaEventualBV.setValor(e.getValor());
            despesaEventualBV.setEmissao(emissao);
            despesaEventualBV.setVencimento(emissao);
            despesaEventualBV.setHistorico(e.getObservacao());
            despesaEventualBV.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
            e.setCobrancaFixa(despesaEventualBV.construirComID());
        }
    }

    public void fechar() {
        try {
            removeDaSessao();
            RequestContext.getCurrentInstance().closeDialog(null);
        } catch (FDadoInvalidoException ex) {
            ex.print();
        }
    }

    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("model", FacesContext.getCurrentInstance());
        SessionUtil.remove("emissao", FacesContext.getCurrentInstance());
        SessionUtil.remove("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        SessionUtil.remove("modalidadeDeCobrancaFixa", FacesContext.getCurrentInstance());
        SessionUtil.remove("naturezaFinanceira", FacesContext.getCurrentInstance());
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public CreditoBV getCredito() {
        return credito;
    }

    public void setCredito(CreditoBV credito) {
        this.credito = credito;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public BoletoDeCartao getBoletoDeCartao() {
        return boletoDeCartao;
    }

    public void setBoletoDeCartao(BoletoDeCartao boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public ModalidadeDeCobranca getModalidadeDeCobranca() {
        return modalidadeDeCobranca;
    }

    public void setModalidadeDeCobranca(ModalidadeDeCobranca modalidadeDeCobranca) {
        this.modalidadeDeCobranca = modalidadeDeCobranca;
    }

    public Model<TipoDeCobranca> getModel() {
        return model;
    }

    public void setModel(Model<TipoDeCobranca> model) {
        this.model = model;
    }

    public ConfiguracaoService getServiceConf() {
        return serviceConf;
    }

    public void setServiceConf(ConfiguracaoService serviceConf) {
        this.serviceConf = serviceConf;
    }

    public ModalidadeDeCobrancaFixa getModalidadeDeCobrancaFixa() {
        return modalidadeDeCobrancaFixa;
    }

    public void setModalidadeDeCobrancaFixa(ModalidadeDeCobrancaFixa modalidadeDeCobrancaFixa) {
        this.modalidadeDeCobrancaFixa = modalidadeDeCobrancaFixa;
    }

    public String getSaldoDeCredito() throws EDadoInvalidoException {
        if (credito.getPessoa() != null) {
            return MoedaFormatter.format(serviceConf.buscar().getMoedaPadrao(), creditoService.buscarSaldo(credito.getPessoa()));
        }
        return MoedaFormatter.format(serviceConf.buscar().getMoedaPadrao(), BigDecimal.ZERO);
    }

    public DespesaEventualBV getDespesaEventualBV() {
        return despesaEventualBV;
    }

    public void setDespesaEventualBV(DespesaEventualBV despesaEventualBV) {
        this.despesaEventualBV = despesaEventualBV;
    }

    public DespesaProvisionada getDespesaProvisionada() {
        return despesaProvisionada;
    }

    public void setDespesaProvisionada(DespesaProvisionada despesaProvisionada) {
        this.despesaProvisionada = despesaProvisionada;
    }

    public ReceitaEventualBV getReceitaEventualBV() {
        return receitaEventualBV;
    }

    public void setReceitaEventualBV(ReceitaEventualBV receitaEventualBV) {
        this.receitaEventualBV = receitaEventualBV;
    }

    public ReceitaProvisionada getReceitaProvisionada() {
        return receitaProvisionada;
    }

    public void setReceitaProvisionada(ReceitaProvisionada receitaProvisionada) {
        this.receitaProvisionada = receitaProvisionada;
    }

    public List<Conta> getContaComCotacao() {
        return contaComCotacao;
    }

    public void setContaComCotacao(List<Conta> contaComCotacao) {
        this.contaComCotacao = contaComCotacao;
    }

    public NaturezaFinanceira getRecebimentoOuPagamento() {
        return recebimentoOuPagamento;
    }

    public void setRecebimentoOuPagamento(NaturezaFinanceira recebimentoOuPagamento) {
        this.recebimentoOuPagamento = recebimentoOuPagamento;
    }

}
