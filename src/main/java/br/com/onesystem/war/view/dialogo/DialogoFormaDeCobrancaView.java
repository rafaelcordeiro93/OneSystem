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
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.builder.CreditoBV;
import br.com.onesystem.war.builder.FormaDeCobrancaBV;
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
public class DialogoFormaDeCobrancaView extends BasicMBImpl<FormaDeCobranca, FormaDeCobrancaBV> implements Serializable {

    private NaturezaFinanceira recebimentoOuPagamento;
    private CreditoBV credito;
    private Titulo titulo;
    private BoletoDeCartaoBV boletoDeCartao;
    private ChequeBV cheque;
    private Date emissao;
    private ModalidadeDeCobranca modalidadeDeCobranca;
    private Model<FormaDeCobranca> model;
    private List<Cotacao> cotacaoLista;
    private List<Conta> contaComCotacao;
    private Conta conta;

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
        e = new FormaDeCobrancaBV();
        credito = new CreditoBV();
        cheque = new ChequeBV();
        boletoDeCartao = new BoletoDeCartaoBV();
    }

    private void buscaDaSessao() throws DadoInvalidoException {
        recebimentoOuPagamento = (NaturezaFinanceira) SessionUtil.getObject("naturezaFinanceira", FacesContext.getCurrentInstance());
        modalidadeDeCobranca = (ModalidadeDeCobranca) SessionUtil.getObject("modalidadeDeCobranca", FacesContext.getCurrentInstance());
        if (modalidadeDeCobranca != null) {
            emissao = (Date) SessionUtil.getObject("emissao", FacesContext.getCurrentInstance());
            model = (Model<FormaDeCobranca>) SessionUtil.getObject("modelForma", FacesContext.getCurrentInstance());
            if (model != null) {
                t = (FormaDeCobranca) model.getObject();
                e = new FormaDeCobrancaBV(model.getObject());
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
                } else if (model.getObject().getCobranca() instanceof BoletoDeCartao) {
                    boletoDeCartao = new BoletoDeCartaoBV((BoletoDeCartao) model.getObject().getCobranca());
                    modalidadeDeCobranca = ModalidadeDeCobranca.CARTAO;
                } else if (model.getObject().getCobranca() instanceof Credito) {
                    credito = new CreditoBV((Credito) model.getObject().getCobranca());
                    modalidadeDeCobranca = ModalidadeDeCobranca.CREDITO;
                } else {
                    cheque = new ChequeBV((Cheque) model.getObject().getCobranca());
                    modalidadeDeCobranca = ModalidadeDeCobranca.CHEQUE;
                }
            }
        }

        cotacaoLista = new CotacaoDAO().naEmissao(emissao).porCotacaoEmpresa().listaDeResultados();
        contaComCotacao = new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
        cotacaoPadrao = new CotacaoDAO().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(emissao).porCotacaoEmpresa().resultado();
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

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoFormaDeCobranca", opcoes, null);
    }

    public List<ModalidadeDeCobranca> getModalidadesDeCobranca() {
        return Arrays.asList(ModalidadeDeCobranca.values());
    }

    public void selecionaCobrancaNoObjeto() {
        Cobranca c = e.getCobranca();
        if (c instanceof Titulo) {
            titulo = (Titulo) c;
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            String id = event.getComponent().getId();
            if (obj instanceof Cobranca) {
                e.setCobranca((Cobranca) event.getObject());
                selecionaCobrancaNoObjeto();
            } else if (obj instanceof Pessoa && id.equals("inp-Credito-search")) {
                credito.setPessoa((Pessoa) obj);
            } else if (obj instanceof Pessoa && id.equals("inp-Cheque-search")) {
                cheque.setPessoa((Pessoa) obj);
            } else if (obj instanceof Pessoa && id.equals("inp-PessoaCartao-search")) {
                boletoDeCartao.setPessoa((Pessoa) obj);
            } else if (obj instanceof Banco) {
                cheque.setBanco((Banco) obj);
            } else if (obj instanceof Cartao) {
                Cotacao resultado = new CotacaoDAO().porConta(((Cartao) obj).getConta()).naMaiorEmissao(emissao).resultado();
                if (resultado != null) {
                    boletoDeCartao.setCartao((Cartao) obj);
                    boletoDeCartao.setCotacao(new CotacaoDAO().porConta(((Cartao) obj).getConta()).naMaiorEmissao(emissao).resultado());
                    e.setCotacao(new CotacaoDAO().porMoeda(((Cartao) obj).getConta().getMoeda()).porCotacaoEmpresa().naMaiorEmissao(emissao).resultado());
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Cotacao_Da_Conta_Do_Cartao_Not_Null"));
                }
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
        if (null != modalidadeDeCobranca) {
            switch (modalidadeDeCobranca) {
                case TITULO:
                    titulo.atualizaSaldo(e.getValor());
                    e.setOperacaoFinanceira(titulo.getOperacaoFinanceira());
                    e.setCobranca(titulo);
                    break;
                case CHEQUE:
                    cheque.setValor(e.getValor());
                    cheque.setHistorico(e.getObservacao());
                    cheque.setCotacao(e.getCotacao());
                    cheque.setTipoLancamento(TipoLancamento.RECEBIDA);
                    cheque.setTipoSituacao(EstadoDeCheque.ABERTO);
                    if (recebimentoOuPagamento == NaturezaFinanceira.RECEITA) {
                        cheque.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
                        e.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
                    } else {
                        cheque.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
                        e.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
                    }
                    e.setCobranca(cheque.construirComID());
                    break;
                case CARTAO:
                    boletoDeCartao.setValor(e.getValor());
                    boletoDeCartao.setSituacao(SituacaoDeCartao.ABERTO);
                    boletoDeCartao.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
                    e.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
                    e.setCobranca(boletoDeCartao.construirComID());
                    break;
                case CREDITO:
                    credito.setValor(e.getValor());
                    credito.setEmissao(emissao);
                    credito.setVencimento(emissao);
                    credito.setCotacao(e.getCotacao());
                    credito.setHistorico(e.getObservacao());
                    if (recebimentoOuPagamento == NaturezaFinanceira.RECEITA) {
                        credito.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
                        e.setOperacaoFinanceira(OperacaoFinanceira.ENTRADA);
                    } else if (recebimentoOuPagamento == NaturezaFinanceira.DESPESA) {
                        credito.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
                        e.setOperacaoFinanceira(OperacaoFinanceira.SAIDA);
                    }
                    e.setCobranca(credito.construirComID());
                    break;
                default:
                    break;
            }
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

    public BoletoDeCartaoBV getBoletoDeCartao() {
        return boletoDeCartao;
    }

    public void setBoletoDeCartao(BoletoDeCartaoBV boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
    }

    public ChequeBV getCheque() {
        return cheque;
    }

    public void setCheque(ChequeBV cheque) {
        this.cheque = cheque;
    }

    public ModalidadeDeCobranca getModalidadeDeCobranca() {
        return modalidadeDeCobranca;
    }

    public void setModalidadeDeCobranca(ModalidadeDeCobranca modalidadeDeCobranca) {
        this.modalidadeDeCobranca = modalidadeDeCobranca;
    }

    public Model<FormaDeCobranca> getModel() {
        return model;
    }

    public void setModel(Model<FormaDeCobranca> model) {
        this.model = model;
    }

    public ConfiguracaoService getServiceConf() {
        return serviceConf;
    }

    public void setServiceConf(ConfiguracaoService serviceConf) {
        this.serviceConf = serviceConf;
    }

    public String getSaldoDeCredito() throws EDadoInvalidoException {
        if (credito.getPessoa() != null) {
            return MoedaFormatter.format(serviceConf.buscar().getMoedaPadrao(), creditoService.buscarSaldo(credito.getPessoa()));
        }
        return MoedaFormatter.format(serviceConf.buscar().getMoedaPadrao(), BigDecimal.ZERO);
    }

    public List<Conta> getContaComCotacao() {
        return contaComCotacao;
    }

    public void setContaComCotacao(List<Conta> contaComCotacao) {
        this.contaComCotacao = contaComCotacao;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public NaturezaFinanceira getRecebimentoOuPagamento() {
        return recebimentoOuPagamento;
    }

    public void setRecebimentoOuPagamento(NaturezaFinanceira recebimentoOuPagamento) {
        this.recebimentoOuPagamento = recebimentoOuPagamento;
    }

}
