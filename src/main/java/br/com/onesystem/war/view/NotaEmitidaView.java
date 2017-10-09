/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.LayoutDeImpressao;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.NumeracaoDeNotaFiscal;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.domain.builder.CobrancaBuilder;
import br.com.onesystem.exception.CurrencyMissmatchException;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.services.GeradorDeEstoque;
import br.com.onesystem.war.builder.ValorPorCotacaoBV;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.DateUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.ImpressoraDeLayout;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.Money;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.builder.CreditoBV;
import br.com.onesystem.war.builder.EstoqueBV;
import br.com.onesystem.war.builder.ItemDeNotaBV;
import br.com.onesystem.war.builder.ItemOrcadoBV;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.builder.ItemDeComandaBV;
import br.com.onesystem.war.builder.ItemDeCondicionalBV;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.CreditoService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.OperacaoDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.util.UsuarioLogadoUtil;
import br.com.onesystem.valueobjects.TipoImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import br.com.onesystem.war.builder.NumeracaoDeNotaFiscalBV;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.LayoutDeImpressaoService;
import br.com.onesystem.war.service.LoteNotaFiscalService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class NotaEmitidaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    private CreditoBV creditoBV;
    private NotaEmitida nota;
    private NotaEmitida notaEmitidaSelecionada;
    private NotaEmitidaBV notaEmitida;
    private ItemDeNotaBV itemEmitido;
    private ItemDeNota itemEmitidoSelecionado;
    private EstoqueBV estoqueBV;
    private ValorPorCotacaoBV cotacaoValoresSelecionado;
    private List<Cotacao> cotacaoLista;
    private List<ValorPorCotacaoBV> cotacoes;
    private List<CobrancaBV> cobrancas;
    private CobrancaBV cobrancaSelecionada;
    private CobrancaBV cobrancaBV;
    private BoletoDeCartaoBV boletoDeCartao;
    private Cotacao cotacao;
    private Orcamento orcamento;
    private String historico;
    private ChequeBV cheque;
    private Cheque chequeSelecionado;
    private boolean editarItensEParcelas;
    private Comanda comandaSelecionada;
    private Condicional condicionalSelecionada;
    private LayoutDeImpressao layout;
    private LayoutDeImpressao layoutTitulo;
    private boolean buscouDeposito = false;

    @Inject
    private Configuracao configuracao;

    @Inject
    private ConfiguracaoVenda configuracaoVenda;

    @Inject
    private CotacaoService service;

    @Inject
    private CreditoService creditoService;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private ConfiguracaoEstoque configuracaoEstoque;

    @Inject
    private LayoutDeImpressaoService serviceLayout;
    private Cotacao cotacaoDeTitulo;

    @Inject
    private CotacaoDAO cotacaoDAO;

    @Inject
    private AdicionaDAO<NotaEmitida> adicionaDAO;

    @Inject
    private AtualizaDAO<NumeracaoDeNotaFiscal> atualizaNumeracaoDeNotaFiscalDAO;

    @Inject
    private UsuarioLogadoUtil usuarioLogado;

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    @Inject
    private ItemService itemService;

    @Inject
    private GeradorDeEstoque geradorDeEstoque;

    @Inject
    private LoteNotaFiscalService loteNotaFiscalService;

    // ---------------------- Inicializa Janela -------------------------------
    @PostConstruct
    public void init() {
        iniciarConfiguracoes();
        limparJanela();
    }

    private void iniciarConfiguracoes() {
        try {
            cotacao = service.getCotacaoPadrao(new Date());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void limparJanela() {
        try {
            notaEmitida = new NotaEmitidaBV();
            notaEmitida.setEmissao(new Date());
            notaEmitida.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            notaEmitida.setUsuario(usuarioLogado.getUsuario());
            notaEmitida.setCaixa((Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance()));
            notaEmitida.setMoedaPadrao(configuracao.getMoedaPadrao());
            notaEmitida.setListaDePreco(configuracaoEstoque.getListaDePreco());
            notaEmitida.setCotacao(cotacao);
            comandaSelecionada = null;
            creditoBV = new CreditoBV();
            itemEmitido = new ItemDeNotaBV();
            boletoDeCartao = new BoletoDeCartaoBV();
            cobrancas = new ArrayList<>();
            notaEmitidaSelecionada = null;
            cheque = new ChequeBV();
            inicializaCotacoes();
            cobrancaBV = new CobrancaBV();
            cobrancaBV.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            orcamento = null;
            editarItensEParcelas = false;
            limparChequeEntrada();
            limparChequeParcelas();
            limparItemDeNota();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void inicializaCotacoes() {
        cotacaoLista = service.buscarCotacoesDoDiaAtual();
        cotacoes = new ArrayList<>();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new ValorPorCotacaoBV(c, null, null, null, configuracao.getMoedaPadrao()));
        }
    }

    // --------------------- Fim Inicializa Janela ----------------------------
    // -------------- Operações para criação da entidade ----------------------   
    public void validaAFaturar() {
        try {
            nota = notaEmitida.construir();
            if (!notaEmitida.getOperacao().getOperacaoFinanceira().equals(OperacaoFinanceira.SEM_ALTERACAO)) {
                // Se valor a faturar maior que zero deve exibir diálogo de confirmação
                if (notaEmitida.getAFaturar() != null && notaEmitida.getAFaturar().compareTo(BigDecimal.ZERO) > 0) {
                    RequestContext c = RequestContext.getCurrentInstance();
                    c.execute("PF('existeValorAFaturar').show()");
                } else if (notaEmitida.getAFaturar() != null && notaEmitida.getAFaturar().compareTo(BigDecimal.ZERO) < 0) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Valor_A_Faturar_Menor_Que_Zero"));
                } else {
                    validaDinheiro();
                }
            } else {
                add();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void validaDinheiro() {
        // Se existir valor em dinheiro abre a janela de cotações.
        if (notaEmitida.getTotalEmDinheiro() != null && notaEmitida.getTotalEmDinheiro().compareTo(BigDecimal.ZERO) > 0) {
            recalculaCotacoes(); // abre a janela de cotações
        } else {
            geraBoletoECreditoAVista();
        }
    }

    public void geraBoletoECreditoAVista() {
        try {
            //Constroi boleto de Cartão
            if (boletoDeCartao.getValor() != null && boletoDeCartao.getValor().compareTo(BigDecimal.ZERO) > 0) {
                if (boletoDeCartao.getCartao() != null) {
                    boletoDeCartao.setCotacao(cotacaoDAO.porConta(boletoDeCartao.getCartao().getConta()).naMaiorEmissao(notaEmitida.getEmissao()).resultado());
                }
                nota.adiciona(boletoDeCartao.construir());
            }

            if (creditoBV.getValor() != null && creditoBV.getValor().compareTo(BigDecimal.ZERO) > 0) {
                creditoBV.setOperacaoFinanceira(notaEmitida.getOperacao().getOperacaoFinanceira());
                creditoBV.setPessoa(notaEmitida.getPessoa());
                creditoBV.setCotacao(cotacao);
                creditoBV.setEntrada(true);
                nota.adiciona(creditoBV.construir());
            }

            geraParcelas();
        } catch (DadoInvalidoException die) {
            die.printConsole();
            die.print();
        }

    }

    /**
     * Gera as parcelas para persistência.
     *
     */
    public void geraParcelas() {
        try {
            //Gera as cobranca de acordo a sua modalidade.
            for (CobrancaBV p : cobrancas) {
                switch (p.getModalidadeDeCobranca()) {
                    case CARTAO:
                        if (p.getCartao() != null) {
                            Cotacao resultado = cotacaoDAO.porConta(p.getCartao().getConta()).naMaiorEmissao(notaEmitida.getEmissao()).resultado();
                            if (resultado != null) {
                                p.setCotacao(resultado);
                            } else {
                                throw new EDadoInvalidoException(new BundleUtil().getMessage("Cotacao_Da_Conta_Do_Cartao_Not_Null"));
                            }
                        }
                        nota.adiciona(p.construirBoletoDeCartao());
                        break;
                    case CHEQUE:
                        nota.adiciona(p.construirCheque());
                        break;
                    case TITULO:
                        nota.adiciona(p.construirTitulo());
                        break;
                    default:
                        break;
                }
            }

            geraOrcamento();
        } catch (DadoInvalidoException die) {
            ErrorMessage.print(new BundleUtil().getMessage("Erro_ao_gerar_parcelas"));
            die.print();
        }
    }

    public void geraOrcamento() {
        if (notaEmitida.getOrcamento() != null) {
            RequestContext.getCurrentInstance().execute("PF('historicoDeOrcamento').show()");
        } else {
            add();
        }
    }

    public void efetivaOrcamento() {
        try {
            nota.getOrcamento().efetiva(historico);
            add();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    /**
     * Prepara valor de recebimento e insere a entidade no banco de dados
     */
    public void add() {
        try {
            geradorDeEstoque.geraEstoqueDe(nota);
            buscaProximoNumeroNF(nota);
            adicionaDAO.adiciona(nota);
            nota.getLoteNotaFiscal().atualizaNumeracao(nota.getFilial());
            //atualizaNumeroNotaFiscal();
            InfoMessage.adicionado();
            limparJanela();
            layout = serviceLayout.getLayoutPorTipoDeLayout(TipoLayout.NOTA_EMITIDA);
            layoutTitulo = serviceLayout.getLayoutPorTipoDeLayout(TipoLayout.TITULO);
            if (!layout.getTipoImpressao().equals(TipoImpressao.NADA_A_FAZER)) {
                RequestContext.getCurrentInstance().execute("document.getElementById('conteudo:ne:imprimir').click()"); // chama a impressao da nota
            }
            if (nota.getCobrancas() != null && !nota.getCobrancas().isEmpty()) {

                List<CobrancaVariavel> lista = nota.getCobrancas().stream().filter(c -> c.getModalidade().equals(ModalidadeDeCobranca.TITULO)).collect(Collectors.toList());
                if (!lista.isEmpty()) {
                    if (!layoutTitulo.getTipoImpressao().equals(TipoImpressao.NADA_A_FAZER)) {
                        RequestContext.getCurrentInstance().execute("PF('imprimirTitulosDialog').show()"); // chama a impressao dos titulos
                    }
                }
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void imprimir() {
        try {
            new ImpressoraDeLayout(nota.getItens(), layout).addParametro("notaEmitida", nota).visualizarPDF();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void imprimirTitulos() {
        try {
            List<CobrancaVariavel> titulos = nota.getCobrancas().stream().filter(c -> c.getModalidade().equals(ModalidadeDeCobranca.TITULO)).collect(Collectors.toList());
            new ImpressoraDeLayout(titulos, layoutTitulo).visualizarPDF();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    /**
     * Calcula a cotacao inicial ao abrir a janela de cotacoes
     */
    public void calculaCotacaoInicial() {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("cotacaoVal");
        if (notaEmitida.getTotalEmDinheiro().compareTo(getTotalConvertidoRecebido()) > 0) {
            for (ValorPorCotacaoBV c : cotacoes) {
                if (c.getCotacao().getConta().getMoeda().equals(configuracao.getMoedaPadrao())) {
                    c.setValorAReceber(notaEmitida.getTotalEmDinheiro());
                    c.setTotalConvertidoRecebido(getTotalConvertidoRecebido());
                    break;
                }
            }
        }
        rc.update("conteudo:cotacaoValoresData");
        rc.execute("PF('cotacaoVal').show()");
    }

    /**
     * Busca a moeda padrão e abre a janela de cotações com o valor restante na
     * cotação de cada moeda.
     */
    public void recalculaCotacoes() {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("cotacaoVal");
        for (ValorPorCotacaoBV c : cotacoes) {
            c.setTotal(notaEmitida.getTotalEmDinheiro());
            c.setTotalConvertidoRecebido(getTotalConvertidoRecebido());
        }
        rc.update("conteudo:cotacaoValoresData");
        rc.execute("PF('cotacaoVal').show()");
    }

    /**
     * Gera as baixas para o recebimento do valor a vista.
     *
     * @throws br.com.onesystem.exception.DadoInvalidoException
     */
    public void geraBaixaDeCotacoes() throws DadoInvalidoException {
        for (ValorPorCotacaoBV c : cotacoes) {
            if (c.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
                nota.adiciona(c.construir());
            }
        }
        geraBoletoECreditoAVista();
    }

    // -------------- Fim Operações para criação da entidade ------------------
    // ---------------------- Forma de Recebimento ----------------------------
    private void calculaTotaisFormaDeRecebimento() {
        if (notaEmitida.getFormaDeRecebimento() != null) {
            FormaDeRecebimento formaDeRecebimento = notaEmitida.getFormaDeRecebimento();
            if ((formaDeRecebimento.getPorcentagemDeEntrada() != null && formaDeRecebimento.getPorcentagemDeEntrada().compareTo(BigDecimal.ZERO) > 0)
                    && notaEmitida.getTotalItens().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal cem = new BigDecimal(100);
                BigDecimal p = formaDeRecebimento.getPorcentagemDeEntrada().divide(cem, 2, BigDecimal.ROUND_UP);
                BigDecimal resultado = p.multiply(notaEmitida.getTotalNota());
                incluiValorDeFormaDeRecebimento(resultado);
            }
        }
    }

    private void incluiValorDeFormaDeRecebimento(BigDecimal resultado) {
        switch (notaEmitida.getFormaDeRecebimento().getFormaPadraoDeEntrada()) {
            case DINHEIRO:
                notaEmitida.setTotalEmDinheiro(resultado);
                break;
            case CREDITO:
                creditoBV.setValor(resultado);
                break;
            case CHEQUE:
//                valoresAVista.setCheque(resultado);
                break;
            case A_FATURAR:
                notaEmitida.setAFaturar(resultado);
                break;
            case CARTAO:
//                valoresAVista.setCartao(resultado);
                break;
            default:
                break;
        }
    }

    // ---------------------- Fim Forma de Recebimento ------------------------
    // ----------------------------- Itens ------------------------------------
    public void addItemNaLista() {
        try {
            notaEmitida.adiciona(itemEmitido);
            limparItemDeNota();

            recalculaValores();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemEmitidoSelecionado != null) {
                notaEmitida.atualiza(itemEmitidoSelecionado, itemEmitido.construirComId());
                limparItemDeNota();
                recalculaValores();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemEmitidoSelecionado != null) {
            notaEmitida.remove(itemEmitidoSelecionado);
            limparItemDeNota();
            recalculaValores();
        }
    }

    public void limparItemDeNota() {
        itemEmitido = new ItemDeNotaBV();
        itemEmitidoSelecionado = null;
    }

    // -------------------------- Fim Itens -----------------------------------
    // --------------------------- Parcelas -----------------------------------
    /**
     * Método responsável por abrir o diálogo de detalhamento das cobranca.
     */
    public void detalharParcela() {
        cobrancaBV = new CobrancaBV(cobrancaSelecionada);
        RequestContext req = RequestContext.getCurrentInstance();
        if (cobrancaSelecionada.getModalidadeDeCobranca() == ModalidadeDeCobranca.CHEQUE) {
            req.execute("PF('detalheChequeParcela').show()");
            req.update("conteudo:panelDetCheParcela");
        } else if (cobrancaSelecionada.getModalidadeDeCobranca() == ModalidadeDeCobranca.CARTAO) {

            req.execute("PF('detalheCartaoParcela').show()");
            req.update("conteudo:panelDetCartaoPar");
        }
    }

    /**
     * Método que criará as cobranca automaticamente ao informar e sair do campo
     * número de cobranca.
     */
    public void criaParcelas() {
        try {
            if (notaEmitida.getFormaDeRecebimento().getConta() != null && cotacaoDeTitulo == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Deve_existir_cotacao_na_conta_bancaria") + ": " + notaEmitida.getFormaDeRecebimento().getConta().getNome());
            }
            if (notaEmitida.getAFaturar() != null && (notaEmitida.getAFaturar().compareTo(BigDecimal.ZERO) > 0
                    || getTotalParcelas().compareTo(BigDecimal.ZERO) > 0) && notaEmitida.getNumeroParcelas() > 0) {
                Integer numParcelas = notaEmitida.getNumeroParcelas(); //Número de cobranca
                TipoPeriodicidade tipoPeridiocidade = notaEmitida.getFormaDeRecebimento().getTipoPeriodicidade();
                Integer periodicidade = notaEmitida.getFormaDeRecebimento().getPeriodicidade();

                if (numParcelas != null && numParcelas > 0) {

                    BigDecimal soma = notaEmitida.getAFaturar().add(getTotalParcelas());
                    Money m = Money.valueOf(soma.toString(), "USD");
                    Money[] distribute = m.distribute(numParcelas);

                    // Busca o primeiro vencimento das cobranca
                    Date vencimento = new DateUtil().getPeriodicidadeCalculada(new Date(), tipoPeridiocidade, periodicidade);

                    cobrancas = new ArrayList<>();
                    for (int i = 0; i < numParcelas; i++) {
                        cobrancas.add(new CobrancaBuilder().comID(getIdParcela()).comValor(distribute[i].getAmount())
                                .comVencimento(vencimento).comDias(getDiasDeVencimento(vencimento)).comCotacao(cotacaoDeTitulo != null ? cotacaoDeTitulo : cotacao).comEmissao(notaEmitida.getEmissao())
                                .comTipoFormaDeRecebimentoParcela(notaEmitida.getFormaDeRecebimento().getFormaPadraoDeParcela()).comCodigoTransacao("000000")
                                .comOperacaoFinanceira(notaEmitida.getOperacao().getOperacaoFinanceira()).comCartao(notaEmitida.getFormaDeRecebimento().getCartao())
                                .comSituacaoDeCartao(SituacaoDeCartao.ABERTO).comSituacaoDeCheque(EstadoDeCheque.ABERTO).comPessoa(notaEmitida.getPessoa())
                                .comEntrada(false).comTipoLancamento(TipoLancamento.RECEBIDA).comSituacaoDeCobranca(SituacaoDeCobranca.ABERTO)
                                .comFilial(notaEmitida.getFilial()).comParcela(i + 1).construir());
                        vencimento = new DateUtil().getPeriodicidadeCalculada(vencimento, tipoPeridiocidade, periodicidade);
                    }

                    recalculaValorAFaturar();
                }
            } else {
                cobrancas = new ArrayList<>();
                ErrorMessage.print(new BundleUtil().getMessage("Nao_Existe_Valor_A_Faturar"));
            }
        } catch (CurrencyMissmatchException cme) {
            ErrorMessage.print("Erro ao calcular o valor das parcelas.");
        } catch (NullPointerException npe) {
            ErrorMessage.print(new BundleUtil().getMessage("operacao_not_null"));
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    /**
     * Adiciona o cheque dentro da lista de cheques na janela
     * detalheChequeEntrada.
     */
    public void addChequeEntrada() {
        try {
            //Prepara e constroi cheque
            cheque.setEntrada(true);
            cheque.setEstadoDeCheque(EstadoDeCheque.ABERTO);
            cheque.setOperacaoFinanceira(notaEmitida.getOperacao().getOperacaoFinanceira());
            Cheque c = cheque.construirComID();

            notaEmitida.adiciona(c); //Adiciona cheque a lista
            limparChequeEntrada(); //Limpa cheque
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateChequeEntrada() {
        try {
            if (chequeSelecionado != null) {
                Cheque c = cheque.construirComID(); //Constroi cheque
                notaEmitida.atualiza(chequeSelecionado, c); //Atualiza cheque na lsita
                limparChequeEntrada(); //Limpa cheque
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteChequeEntrada() {
        if (chequeSelecionado != null) {
            notaEmitida.remove(chequeSelecionado); //Remove cheque
        }
        limparChequeEntrada();
    }

    public void limparChequeEntrada() {
        chequeSelecionado = null;
        cheque = new ChequeBV();
        cheque.setCotacao(cotacao);
    }

    public void addCartaoQuandoSelecionadoNaParcela(CobrancaBV parcela) {
        if (parcela.getModalidadeDeCobranca() == ModalidadeDeCobranca.CARTAO) {
            for (CobrancaBV p : cobrancas) {
                if (p.getId().equals(parcela.getId())) {
                    p.setCartao(notaEmitida.getFormaDeRecebimento().getCartao());
                    p.setCodigoTransacao("000000");
                }
            }
        }
    }

    public void limparChequeParcelas() {
        cobrancaBV.setBanco(null);
        cobrancaBV.setAgencia(null);
        cobrancaBV.setNumeroCheque(null);
        cobrancaBV.setConta(null);
        cobrancaBV.setEmitente(null);
        cobrancaBV.setObservacao(null);
    }

    // ------------------------- Fim Parcelas ---------------------------------
    // ----------------------------- Selecao ----------------------------------
    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = event.getObject();
            String idComponent = event.getComponent().getId();
            if (obj instanceof Operacao) {
                Operacao operacao = (Operacao) obj;
                List<OperacaoDeEstoque> operacoesDeEstoque = operacaoDeEstoqueService.buscarOperacoesDeEstoquePor(operacao);
                if (operacoesDeEstoque == null || operacoesDeEstoque.isEmpty()) {
                    RequestContext rc = RequestContext.getCurrentInstance();
                    rc.execute("PF('notaOperacaoNaoRelacionadaDialog').show()");
                } else {
                    setupView(operacao);
                }
            } else if (obj instanceof Pessoa) {
                notaEmitida.setPessoa((Pessoa) obj);
            } else if (obj instanceof ListaDePreco) {
                notaEmitida.setListaDePreco((ListaDePreco) obj);
                atualizaValorDeItemDeNota();
            } else if (obj instanceof Item) {
                itemEmitido.setItem((Item) obj);
                atribuiItemASessao();
                atualizaValorDeItemDeNota();
            } else if (obj instanceof FormaDeRecebimento) {
                FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) obj;
                notaEmitida.setFormaDeRecebimento(formaDeRecebimento);
                calculaTotaisFormaDeRecebimento();
                recalculaValores();
                buscaCotacaoDeTitulo(formaDeRecebimento);
            } else if (obj instanceof Banco && "detbanco-search".equals(idComponent)) {
                cheque.setBanco((Banco) obj);
            } else if (obj instanceof Banco && "bancoParcelas-search".equals(idComponent)) {
                cobrancaBV.setBanco((Banco) obj);
            } else if (obj instanceof Cartao) {
                boletoDeCartao.setCartao((Cartao) obj);
            } else if (obj instanceof NotaEmitida && "importaItemNotaEmitida-btn".equals(idComponent)) {
                importaItensDe((NotaEmitida) obj);
            } else if (obj instanceof NotaEmitida && ("importaNotaEmitida-btn".equals(idComponent) || "importaVendaEntregaFutura-btn".equals(idComponent))) {
                importa((NotaEmitida) obj);
            } else if (obj instanceof Comanda && "importaComanda-btn".equals(idComponent)) {
                importa((Comanda) obj);
            } else if (obj instanceof Condicional && "importaCondicional-btn".equals(idComponent)) {
                importa((Condicional) obj);
            } else if (obj instanceof Orcamento) {
                importa((Orcamento) obj);
            } else if (obj instanceof List && "neQuantidade-btn".equals(idComponent)) {
                List<QuantidadeDeItemPorDeposito> lista = (List<QuantidadeDeItemPorDeposito>) event.getObject();
                itemEmitido.setListaDeQuantidade(lista);
                itemEmitido.setQuantidade(lista.stream().map(QuantidadeDeItemPorDeposito::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add));
                buscouDeposito = true;
            } else if (obj instanceof List && "exibeOrcamento-btn".equals(idComponent)) {
                List<ItemOrcadoBV> lista = (List<ItemOrcadoBV>) event.getObject();
                if (!lista.isEmpty()) {
                    for (ItemOrcadoBV i : lista) {
                        itemEmitido.setItem(i.getItem());
                        itemEmitido.setUnitario(i.getUnitario());
                        itemEmitido.setListaDeQuantidade(i.getQuantidadePorDeposito());
                        itemEmitido.setQuantidade(lista.stream().map((q) -> q.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add));
                        addItemNaLista();

                    }
                    populaCampos(orcamento);
                }
            } else if (obj instanceof List && "exibeNotaEmitida-btn".equals(idComponent)) {
                List<ItemDeNotaBV> lista = (List<ItemDeNotaBV>) event.getObject();
                if (!lista.isEmpty()) {
                    for (ItemDeNotaBV i : lista) {
                        itemEmitido.setItem(i.getItem());
                        itemEmitido.setUnitario(i.getUnitario());
                        itemEmitido.setListaDeQuantidade(i.getListaDeQuantidade());
                        itemEmitido.setQuantidade(lista.stream().map((q) -> q.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add));
                        addItemNaLista();
                    }
                    populaCampos(notaEmitidaSelecionada);
                }
            } else if (obj instanceof List && "exibeComanda-btn".equals(idComponent)) {
                List<ItemDeComandaBV> lista = (List<ItemDeComandaBV>) event.getObject();
                if (!lista.isEmpty()) {
                    for (ItemDeComandaBV i : lista) {
                        itemEmitido.setItem(i.getItem());
                        itemEmitido.setUnitario(i.getUnitario());
                        itemEmitido.setListaDeQuantidade(i.getListaDeQuantidade());
                        itemEmitido.setQuantidade(lista.stream().map((q) -> q.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add));
                        addItemNaLista();
                    }
                    populaCampos(comandaSelecionada);
                }
            } else if (obj instanceof List && "exibeCondicional-btn".equals(idComponent)) {
                List<ItemDeCondicionalBV> lista = (List<ItemDeCondicionalBV>) event.getObject();
                if (!lista.isEmpty()) {
                    for (ItemDeCondicionalBV i : lista) {
                        itemEmitido.setItem(i.getItem());
                        itemEmitido.setUnitario(i.getUnitario());
                        itemEmitido.setListaDeQuantidade(Arrays.asList(new QuantidadeDeItemPorDeposito(new Long(1), new SaldoDeEstoque(new Long(1), configuracaoEstoque.getDepositoPadrao(), BigDecimal.ZERO), i.getAFaturar())));
                        itemEmitido.setQuantidade(lista.stream().map((q) -> q.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add));
                        addItemNaLista();
                    }
                    populaCampos(condicionalSelecionada);
                }
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void buscaCotacaoDeTitulo(FormaDeRecebimento formaDeRecebimento) throws DadoInvalidoException {
        if (formaDeRecebimento.getConta() != null) {
            cotacaoDeTitulo = service.getCotacaoNaUltimaEmissaoPor(formaDeRecebimento.getConta(), new Date());
            if (cotacaoDeTitulo == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Deve_existir_cotacao_na_conta_bancaria") + ": " + formaDeRecebimento.getConta().getNome());
            }
        }
    }

    public void geraListaDeEstoquePadrao() {
        if (!buscouDeposito) {
            itemEmitido.setListaDeQuantidade(Arrays.asList(new QuantidadeDeItemPorDeposito(null, new SaldoDeEstoque(null, configuracaoEstoque.getDepositoPadrao(), null), itemEmitido.getQuantidade())));
        } else {
            buscouDeposito = false;
        }
    }

    /**
     * Atualiza o valor unitario do item de de nota conforme a lista de preço
     * selecionada.
     */
    public void atualizaValorDeItemDeNota() {
        if (notaEmitida.getListaDePreco() != null && itemEmitido.getItem() != null) {
            itemEmitido.setUnitario(itemService.getPreco(itemEmitido.getItem(), notaEmitida.getListaDePreco()));
        }
    }

    public void setupView(Operacao operacao) {
        limparJanela();
        TipoOperacao tipo = operacao.getTipoOperacao();
        editarItensEParcelas = tipo == TipoOperacao.DEVOLUCAO_CLIENTE || tipo == TipoOperacao.ENTREGA_MERCADORIA_VENDIDA
                || tipo == TipoOperacao.DEVOLUCAO_CONDICIONAL;
        notaEmitida.setOperacao(operacao);
        notaEmitida.setLoteNotaFiscal(operacao.getLoteNotaFiscal());
        //buscaProximoNumeroNF();
        RequestContext.getCurrentInstance().update("conteudo");
    }

    private void buscaProximoNumeroNF(NotaEmitida nota) {
        try {
            List<NumeracaoDeNotaFiscal> numeracao = loteNotaFiscalService.buscaLoteNotaFiscalDa(notaEmitida.getOperacao()).getNumeracaoDeNotaFiscal();
            for (NumeracaoDeNotaFiscal nnf : numeracao) {
                if (nnf.getFilial().equals(notaEmitida.getFilial())) {
                    nota.setNumeroNF(nnf.getNumeroNF());
                }
            }
        } catch (NullPointerException npe) {
            npe.getMessage();
        }
    }

    private void atualizaNumeroNotaFiscal() {
        try {
            List<NumeracaoDeNotaFiscal> numeracao = loteNotaFiscalService.buscaLoteNotaFiscalDa(notaEmitida.getOperacao()).getNumeracaoDeNotaFiscal();
            for (NumeracaoDeNotaFiscal nnf : numeracao) {
                if (nnf.getFilial().equals(notaEmitida.getFilial())) {
                    NumeracaoDeNotaFiscalBV nnfBV = new NumeracaoDeNotaFiscalBV(nnf);
                    nnfBV.setNumeroNF(nnf.getNumeroNF() + 1); //acrescenta +1 ao proximo numero da nota fiscal.
                    atualizaNumeracaoDeNotaFiscalDAO.atualiza(nnfBV.construirComID());
                }
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void importaItensDe(NotaEmitida nota) throws DadoInvalidoException {
        for (ItemDeNota ie : nota.getItens()) {
            itemEmitido.setItem(ie.getItem());
            itemEmitido.setUnitario(ie.getUnitario());
            itemEmitido.setQuantidade(ie.getQuantidade());
            List<SaldoDeEstoque> saldos = new EstoqueService().buscaListaDeSaldoDeEstoque(ie.getItem(), new Date());
            for (Estoque e : ie.getEstoques()) {
                for (SaldoDeEstoque s : saldos) {
                    if (e.getDeposito().equals(s.getDeposito())) {
                        itemEmitido.getListaDeQuantidade().add(new QuantidadeDeItemPorDeposito(null, s, ie.getQuantidade()));
                    }
                }
            }
            addItemNaLista();
        }
    }

    private void populaCampos(Orcamento orcamento) {
        notaEmitida.setOrcamento(orcamento);
        notaEmitida.setPessoa(orcamento.getPessoa());
        notaEmitida.setListaDePreco(orcamento.getListaDePreco());
        notaEmitida.setFormaDeRecebimento(orcamento.getFormaDeRecebimento());
        calculaTotaisFormaDeRecebimento();
        recalculaValores();
    }

    private void populaCampos(Nota nota) {
        notaEmitida.setNotaDeOrigem(nota);
        notaEmitida.setPessoa(nota.getPessoa());
        notaEmitida.setListaDePreco(nota.getListaDePreco());

        if (nota.getOperacao().getTipoOperacao() == TipoOperacao.DEVOLUCAO_CLIENTE) {
            notaEmitida.setFormaDeRecebimento(configuracaoVenda.getFormaDeRecebimentoDevolucaoEmpresa());
        }
        calculaTotaisFormaDeRecebimento();
        recalculaValores();
        RequestContext.getCurrentInstance().update("conteudo");
    }

    private void populaCampos(Comanda comanda) {
        notaEmitida.setComanda(comanda);
        notaEmitida.setListaDePreco(comanda.getListaDePreco());
        recalculaValores();
        RequestContext.getCurrentInstance().update("conteudo");
    }

    private void populaCampos(Condicional condicional) {
        notaEmitida.setCondicional(condicional);
        notaEmitida.setPessoa(condicional.getPessoa());
        notaEmitida.setListaDePreco(condicional.getListaDePreco());
        recalculaValores();
        RequestContext.getCurrentInstance().update("conteudo");
    }

    private void importa(Orcamento orcamento) throws DadoInvalidoException {
        this.orcamento = orcamento;
        atribuiOrcamentoASessao(orcamento);
        RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:ne:exibeOrcamento-btn\").click();");
    }

    private void importa(NotaEmitida nota) throws DadoInvalidoException {
        notaEmitidaSelecionada = nota;
        SessionUtil.put(nota, "nota", FacesContext.getCurrentInstance());
        SessionUtil.put(notaEmitida.getOperacao().getTipoOperacao(), "tipoOperacao", FacesContext.getCurrentInstance());
        RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:ne:exibeNotaEmitida-btn\").click();");
    }

    private void importa(Comanda comanda) throws DadoInvalidoException {
        comandaSelecionada = comanda;
        SessionUtil.put(comanda, "comanda", FacesContext.getCurrentInstance());
        SessionUtil.put(notaEmitida.getOperacao().getTipoOperacao(), "tipoOperacao", FacesContext.getCurrentInstance());
        RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:ne:exibeComanda-btn\").click();");
    }

    private void importa(Condicional condicional) throws DadoInvalidoException {
        condicionalSelecionada = condicional;
        SessionUtil.put(condicional, "condicional", FacesContext.getCurrentInstance());
        SessionUtil.put(notaEmitida.getOperacao().getTipoOperacao(), "tipoOperacao", FacesContext.getCurrentInstance());
        RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:ne:exibeCondicional-btn\").click();");
    }

    public void atribuiItemASessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.item.token");
        session.setAttribute("onesystem.item.token", itemEmitido.getItem());
    }

    public void atribuiOrcamentoASessao(Orcamento orcamento) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.orcamento.token");
        session.setAttribute("onesystem.orcamento.token", orcamento);
    }

    public void selecionaChequeDeEntrada(SelectEvent event) {
        chequeSelecionado = (Cheque) event.getObject();
        cheque = new ChequeBV(chequeSelecionado);
    }

    public void selecionaCartao(SelectEvent event) {
        cobrancaBV.setCartao((Cartao) event.getObject());
    }

    public void selecionaItemDeNota(SelectEvent event) {
        this.itemEmitidoSelecionado = (ItemDeNota) event.getObject();
        this.itemEmitido = new ItemDeNotaBV(itemEmitidoSelecionado);
    }

    public void selecionarBanco(SelectEvent event) {
        Banco banco = (Banco) event.getObject();
        cobrancaBV.setBanco(banco);
    }

    public void selecionaChequeEntrada(SelectEvent event) {
        chequeSelecionado = (Cheque) event.getObject();
        cheque = new ChequeBV(chequeSelecionado);
    }

    // ----------------------------- Fim Selecao ------------------------------
    // ------------------ Outras Operações da Janela --------------------------
    public void addDetalheParcelaCartao() {
        try {
            cobrancaBV.setSituacaoDeCartao(SituacaoDeCartao.ABERTO);
            cobrancaBV.construirBoletoDeCartao(); // Constroi Boleto de Cartão para validar.

            //Seleciona o cartao na parcela
            cobrancaSelecionada.setCartao(cobrancaBV.getCartao());
            cobrancaSelecionada.setCodigoTransacao(cobrancaBV.getCodigoTransacao());
            cobrancas.set(cobrancas.indexOf(cobrancaSelecionada), cobrancaSelecionada);
            cobrancaBV = new CobrancaBV();
            cobrancaSelecionada = null;
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void addDetalheParcelaCheque() {
        try {

            //Seleciona o cheque na parcela
            cobrancaSelecionada.setBanco(cobrancaBV.getBanco());
            cobrancaSelecionada.setAgencia(cobrancaBV.getAgencia());
            cobrancaSelecionada.setNumeroCheque(cobrancaBV.getNumeroCheque());
            cobrancaSelecionada.setConta(cobrancaBV.getConta());
            cobrancaSelecionada.setEmitente(cobrancaBV.getEmitente());
            cobrancaSelecionada.setObservacao(cobrancaBV.getObservacao());
            cobrancas.set(cobrancas.indexOf(cobrancaSelecionada), cobrancaSelecionada);
            cobrancaBV.construirCheque(); // Constroi Cheque para validar.

            cobrancaBV = new CobrancaBV();
            cobrancaSelecionada = null;

            RequestContext r = RequestContext.getCurrentInstance();
            r.update("conteudo:ne:neParcelas");
            r.execute("PF('detalheChequeParcela').hide()");

        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    /**
     * Calcula o valor de acréscimo e desconto após informar um dos campos de
     * porcentagem de acréscimo e desconto.
     */
    public void calculaValorAcrescimoEDesconto() {
        BigDecimal total = notaEmitida.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pAcrescimo = notaEmitida.getPorcentagemAcrescimo() == null ? BigDecimal.ZERO : notaEmitida.getPorcentagemAcrescimo();
            BigDecimal pDesconto = notaEmitida.getPorcentagemDesconto() == null ? BigDecimal.ZERO : notaEmitida.getPorcentagemDesconto();
            BigDecimal acrescimo;
            BigDecimal desconto;
            BigDecimal cem = new BigDecimal(100);

            if (pAcrescimo.compareTo(BigDecimal.ZERO) > 0) {
                acrescimo = (pAcrescimo.multiply(total)).divide(cem, 2, BigDecimal.ROUND_UP);
                notaEmitida.setAcrescimo(acrescimo);
            } else {
                notaEmitida.setAcrescimo(BigDecimal.ZERO);
            }
            if (pDesconto.compareTo(BigDecimal.ZERO) > 0) {
                desconto = (pDesconto.multiply((total))).divide(cem, 2, BigDecimal.ROUND_UP);
                notaEmitida.setDesconto(desconto);
            } else {
                notaEmitida.setDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(notaEmitida.getTotalNota());
        }
    }

    /**
     * Calcula a porcentagem de acréscimo e desconto após informar um dos campos
     * de valor de acréscimo e desconto.
     */
    public void calculaPorcentagemAcrescimoEDesconto() {
        BigDecimal total = notaEmitida.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal acrescimo = notaEmitida.getAcrescimo() == null ? BigDecimal.ZERO : notaEmitida.getAcrescimo();
            BigDecimal desconto = notaEmitida.getDesconto() == null ? BigDecimal.ZERO : notaEmitida.getDesconto();
            BigDecimal pAcrescimo;
            BigDecimal pDesconto;
            BigDecimal cem = new BigDecimal(100);

            if (acrescimo.compareTo(BigDecimal.ZERO) > 0) {
                pAcrescimo = (acrescimo.multiply(cem)).divide(total, 2, BigDecimal.ROUND_UP);
                notaEmitida.setPorcentagemAcrescimo(pAcrescimo);
            } else {
                notaEmitida.setPorcentagemAcrescimo(BigDecimal.ZERO);
            }
            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
                pDesconto = (desconto.multiply(cem)).divide(((total)), 2, BigDecimal.ROUND_UP);
                notaEmitida.setPorcentagemDesconto(pDesconto);
            } else {
                notaEmitida.setPorcentagemDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(notaEmitida.getTotalNota());
        }
    }

    public void calculaValoresTotais() {
        incluiValorDeFormaDeRecebimento(notaEmitida.getTotalNota());
    }

    public void recalculaValores() {
        recalculaValorAFaturar();
        if (!cobrancas.isEmpty()) {
            criaParcelas();
        }
    }

    public void deleteBoletoDeCartao() {
        boletoDeCartao = new BoletoDeCartaoBV();
    }

    public void constroiBoletoDeCartaoEntrada() {
        RequestContext rc = RequestContext.getCurrentInstance();
        try {
            boletoDeCartao.setCotacao(cotacao);
            boletoDeCartao.setSituacao(SituacaoDeCartao.ABERTO);
            boletoDeCartao.setPessoa(notaEmitida.getPessoa());
            boletoDeCartao.setOperacaoFinanceira(notaEmitida.getOperacao().getOperacaoFinanceira());
            boletoDeCartao.setEntrada(true);

            if (boletoDeCartao.getCartao() != null) {
                if (boletoDeCartao.getValor() == null || boletoDeCartao.getValor().compareTo(BigDecimal.ZERO) > 0) {
                    if (boletoDeCartao.getCartao().getTaxaDeAdministracao().isEmpty()) {
                        throw new EDadoInvalidoException(new BundleUtil().getMessage("Taxa_Administracao_Not_Null"));
                    } else {
                        for (TaxaDeAdministracao b : boletoDeCartao.getCartao().getTaxaDeAdministracao()) {
                            if (b.getId().equals(new Long(1))) {
                                LocalDate d = LocalDate.now();
                                d = d.plusDays(new Long(b.getNumeroDias()));
                                boletoDeCartao.setVencimento(Date.from(d.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                                break;
                            }
                        }
                    }
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("valor_not_null"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("entrada_cartao_not_null"));
            }
            boletoDeCartao.construir();
            recalculaValores();

            rc.execute("PF('detalheCartaoEntrada').hide()");
        } catch (DadoInvalidoException ex) {
            boletoDeCartao.setValor(null);
            ex.print();
            rc.update("conteudo:growl");
        }
    }

    public void recalculaValorAFaturar() {

        BigDecimal chequeTotal = notaEmitida.getTotalChequeDeEntrada() == null ? BigDecimal.ZERO : notaEmitida.getTotalChequeDeEntrada();
        BigDecimal cartao = boletoDeCartao.getValor() == null ? BigDecimal.ZERO : boletoDeCartao.getValor();
        BigDecimal credito = creditoBV.getValor() == null ? BigDecimal.ZERO : creditoBV.getValor();
        BigDecimal dinheiro = notaEmitida.getTotalEmDinheiro() == null ? BigDecimal.ZERO : notaEmitida.getTotalEmDinheiro();
        BigDecimal totalParcelas = getTotalParcelas() == null ? BigDecimal.ZERO : getTotalParcelas();
        BigDecimal soma = chequeTotal.add(cartao).add(credito).add(totalParcelas).add(dinheiro);

        notaEmitida.setAFaturar(notaEmitida.getTotalNota().subtract(soma));
    }

    // ---------------- Fim Outras Operações da Janela ------------------------
    //----------------------- Getter Personalizados ---------------------------
    public Integer getDiasDeVencimento(Date vencimento) {
        LocalDate venc = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long dias = LocalDate.now().until(venc, ChronoUnit.DAYS);
        return dias.intValue();
    }

    public String getValorRestante() {
        BigDecimal total = notaEmitida.getTotalEmDinheiro();
        BigDecimal valorAReceber = BigDecimal.ZERO;
        NumberFormat nf = NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal());

        for (ValorPorCotacaoBV c : cotacoes) {
            valorAReceber = valorAReceber.add(c.getValorConvertidoRecebido());
        }

        if (total == null || total.subtract(valorAReceber).compareTo(BigDecimal.ZERO) < 0) {
            return nf.format(BigDecimal.ZERO);
        } else {
            return nf.format(total.subtract(valorAReceber));
        }
    }

    public BigDecimal getTotalConvertidoRecebido() {
        BigDecimal total = BigDecimal.ZERO;
        for (ValorPorCotacaoBV c : cotacoes) {
            total = total.add(c.getValorConvertidoRecebido());
        }
        return total;
    }

    public String getTotalConvertidoRecebidoFormatado() {
        return NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(getTotalConvertidoRecebido());
    }

    public BigDecimal getTotalParcelas() {
        BigDecimal totalParcela = BigDecimal.ZERO;
        for (CobrancaBV p : cobrancas) {
            if (p.getCotacao() != null && p.getCotacao() != cotacao) {
                totalParcela = totalParcela.add(p.getValor().divide(p.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
            } else {
                totalParcela = totalParcela.add(p.getValor());
            }
        }

        return totalParcela;
    }

    public String getTotalParcelasFormatado() {
        BigDecimal totalParcelas = getTotalParcelas();

        return totalParcelas.compareTo(BigDecimal.ZERO) == 0 ? ""
                : NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(totalParcelas);
    }

    private Long getIdParcela() {
        Long id = (long) 1;
        if (!cobrancas.isEmpty()) {
            for (CobrancaBV p : cobrancas) {
                if (p.getId() >= id) {
                    id = p.getId() + 1;
                }
            }
        }
        return id;
    }

    //------------------- Fim Getter Personalizados ---------------------------
    //----------------------- Getters and Setters -----------------------------
    public String getSaldoDeCredito() {
        if (notaEmitida.getPessoa() != null) {
            return MoedaFormatter.format(cotacao.getConta().getMoeda(), creditoService.buscarSaldo(notaEmitida.getPessoa()));
        }
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), BigDecimal.ZERO);
    }

    public NotaEmitida getNotaEmitidaSelecionada() {
        return notaEmitidaSelecionada;
    }

    public void setNotaSelecionada(NotaEmitida notaEmitidaSelecionada) {
        this.notaEmitidaSelecionada = notaEmitidaSelecionada;
    }

    public NotaEmitidaBV getNotaEmitida() {
        return notaEmitida;
    }

    public List<ModalidadeDeCobranca> getTiposDeFormaDeRecebimentoParcela() {
        List<ModalidadeDeCobranca> forma = new ArrayList<>();
        if (notaEmitida.getFormaDeRecebimento().isParcelaEmCartao()) {
            forma.add(ModalidadeDeCobranca.CARTAO);
        }
        if (notaEmitida.getFormaDeRecebimento().isParcelaEmCheque()) {
            forma.add(ModalidadeDeCobranca.CHEQUE);
        }
        if (notaEmitida.getFormaDeRecebimento().isParcelaEmConta()) {
            forma.add(ModalidadeDeCobranca.TITULO);
        }
        return forma;
    }

    public boolean getGeraFinanceiro() {
        try {
            return notaEmitida.getOperacao().getTipoOperacao() == TipoOperacao.ENTREGA_MERCADORIA_VENDIDA
                    || (notaEmitida.getCondicional() != null && notaEmitida.getOperacao().getTipoOperacao() == TipoOperacao.DEVOLUCAO_CONDICIONAL);
        } catch (NullPointerException npe) {
            return false;
        }
    }

    public void setNota(NotaEmitidaBV notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public ItemDeNotaBV getItemEmitido() {
        return itemEmitido;
    }

    public void setItemEmitido(ItemDeNotaBV itemEmitido) {
        this.itemEmitido = itemEmitido;
    }

    public ItemDeNota getItemEmitidoSelecionado() {
        return itemEmitidoSelecionado;
    }

    public void setItemEmitidoSelecionado(ItemDeNota itemEmitidoSelecionado) {
        this.itemEmitidoSelecionado = itemEmitidoSelecionado;
    }

    public EstoqueBV getEstoqueBV() {
        return estoqueBV;
    }

    public void setEstoqueBV(EstoqueBV estoqueBV) {
        this.estoqueBV = estoqueBV;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ValorPorCotacaoBV getCotacaoValoresSelecionado() {
        return cotacaoValoresSelecionado;
    }

    public void setCotacaoValoresSelecionado(ValorPorCotacaoBV cotacaoValoresSelecionado) {
        this.cotacaoValoresSelecionado = cotacaoValoresSelecionado;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public List<ValorPorCotacaoBV> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<ValorPorCotacaoBV> cotacoes) {
        this.cotacoes = cotacoes;
    }

    public List<CobrancaBV> getCobrancas() {
        return cobrancas;
    }

    public void setCobrancas(List<CobrancaBV> cobrancas) {
        this.cobrancas = cobrancas;
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }

    public ChequeBV getCheque() {
        return cheque;
    }

    public void setCheque(ChequeBV cheque) {
        this.cheque = cheque;
    }

    public Cheque getChequeSelecionado() {
        return chequeSelecionado;
    }

    public void setChequeSelecionado(Cheque chequeSelecionado) {
        this.chequeSelecionado = chequeSelecionado;
    }

    public CobrancaBV getCobrancaSelecionada() {
        return cobrancaSelecionada;
    }

    public void setCobrancaSelecionada(CobrancaBV cobrancaSelecionada) {
        this.cobrancaSelecionada = cobrancaSelecionada;
    }

    public CobrancaBV getCobrancaBV() {
        return cobrancaBV;
    }

    public void setCobrancaBV(CobrancaBV cobrancaBV) {
        this.cobrancaBV = cobrancaBV;
    }

    public BoletoDeCartaoBV getBoletoDeCartao() {
        return boletoDeCartao;
    }

    public void setBoletoDeCartao(BoletoDeCartaoBV boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public CreditoBV getCreditoBV() {
        return creditoBV;
    }

    public void setCreditoBV(CreditoBV creditoBV) {
        this.creditoBV = creditoBV;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public EstoqueService getServiceEstoque() {
        return serviceEstoque;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

    public EstadoDeOrcamento getEstadoDeOrcamento() {
        return EstadoDeOrcamento.EFETIVADO;
    }

    public boolean isEditarItensEParcelas() {
        return editarItensEParcelas;
    }

    public void setEditarItensEParcelas(boolean devolucao) {
        this.editarItensEParcelas = devolucao;
    }

    public ConfiguracaoVenda getConfiguracaoVenda() {
        return configuracaoVenda;
    }

    public void setConfiguracaoVenda(ConfiguracaoVenda configuracaoVenda) {
        this.configuracaoVenda = configuracaoVenda;
    }

    public CreditoService getCreditoService() {
        return creditoService;
    }

    public void setCreditoService(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    //------------------- Fim Getters and Setters -----------------------------
}
