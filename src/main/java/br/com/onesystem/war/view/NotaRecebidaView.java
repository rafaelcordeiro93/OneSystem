/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.ArmazemDeRegistrosNaMemoria;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.ParcelaDePedido;
import br.com.onesystem.domain.PedidoAFornecedores;
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
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.Money;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.builder.CreditoBV;
import br.com.onesystem.war.builder.EstoqueBV;
import br.com.onesystem.war.builder.ItemDeNotaBV;
import br.com.onesystem.war.builder.NotaRecebidaBV;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.OperacaoDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.util.UsuarioLogadoUtil;
import br.com.onesystem.war.builder.ItemDePedidoBV;
import br.com.onesystem.war.builder.LoteItemBV;
import br.com.onesystem.war.service.LoteItemService;
import br.com.onesystem.war.view.selecao.SelecaoItemView;
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
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class NotaRecebidaView extends BasicMBImpl<NotaRecebida, NotaRecebidaBV> implements Serializable {

    private CreditoBV creditoBV;
    private NotaRecebida nota;
    private NotaRecebida notaRecebidaSelecionada;
    private NotaRecebidaBV notaRecebida;
    private ItemDeNotaBV itemRecebido;
    private ItemDeNota itemRecebidoSelecionado;
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
    private PedidoAFornecedores pedidoAFornecedores;
    private boolean buscouDeposito = false;
    private Cotacao cotacaoDeTitulo;
    private LoteItemBV loteItemBV;

    @Inject
    private Configuracao configuracao;

    @Inject
    private CotacaoService service;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private ConfiguracaoEstoque configuracaoEstoque;

    @Inject
    private GeradorDeEstoque geradorDeEstoque;

    @Inject
    private UsuarioLogadoUtil usuarioLogado;

    @Inject
    private CotacaoDAO cotacaoDAO;

    @Inject
    private LoteItemService loteItemService;

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    @Inject
    private AtualizaDAO<PedidoAFornecedores> atualizaPedido;

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
            notaRecebida = new NotaRecebidaBV();
            notaRecebida.setCaixa((Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance()));
            notaRecebida.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
            notaRecebida.setUsuario(usuarioLogado.getUsuario());
            notaRecebida.setCotacao(cotacao);
            creditoBV = new CreditoBV();
            itemRecebido = new ItemDeNotaBV();
            boletoDeCartao = new BoletoDeCartaoBV();
            cobrancas = new ArrayList<>();
            notaRecebidaSelecionada = null;
            cheque = new ChequeBV();
            inicializaCotacoes();
            limparChequeEntrada();
            cobrancaBV = new CobrancaBV();
            loteItemBV = new LoteItemBV();
            cobrancaBV.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
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
    public void validaAFaturar() throws DadoInvalidoException {
        notaRecebida.setEmissao(new Date());
        nota = notaRecebida.construir();
        // Se valor a faturar maior que zero deve exibir diálogo de confirmação
        if (notaRecebida.getAFaturar() != null && notaRecebida.getAFaturar().compareTo(BigDecimal.ZERO) > 0) {
            RequestContext c = RequestContext.getCurrentInstance();
            c.execute("PF('existeValorAFaturar').show()");
        } else if (notaRecebida.getAFaturar() != null && notaRecebida.getAFaturar().compareTo(BigDecimal.ZERO) < 0) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Valor_A_Faturar_Menor_Que_Zero"));
        } else {
            validaDinheiro();
        }
    }

    public void validaDinheiro() {
        // Se existir valor em dinheiro abre a janela de cotações.
        if (notaRecebida.getTotalEmDinheiro() != null && notaRecebida.getTotalEmDinheiro().compareTo(BigDecimal.ZERO) > 0) {
            recalculaCotacoes(); // abre a janela de cotações
        } else {
            geraBoletoDeCartaoAVista();
        }
    }

    public void geraBoletoDeCartaoAVista() {
        try {
            //Constroi boleto de Cartão
            if (boletoDeCartao.getValor() != null && boletoDeCartao.getValor().compareTo(BigDecimal.ZERO) > 0) {
                if (boletoDeCartao.getCartao() != null) {
                    boletoDeCartao.setCotacao(cotacaoDAO.porConta(boletoDeCartao.getCartao().getConta()).naMaiorEmissao(notaRecebida.getEmissao()).resultado());
                }
                nota.adiciona(boletoDeCartao.construir());
            }

            if (creditoBV.getValor() != null && creditoBV.getValor().compareTo(BigDecimal.ZERO) > 0) {
                creditoBV.setOperacaoFinanceira(notaRecebida.getOperacao().getOperacaoFinanceira());
                creditoBV.setPessoa(notaRecebida.getPessoa());
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
                            p.setCotacao(cotacaoDAO.porConta(p.getCartao().getConta()).naMaiorEmissao(notaRecebida.getEmissao()).resultado());
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

            add();
        } catch (DadoInvalidoException die) {
            ErrorMessage.print(new BundleUtil().getMessage("Erro_ao_gerar_parcelas"));
            die.print();
        }
    }

    /**
     * Prepara valor de recebimento e insere a entidade no banco de dados
     */
    public void add() {
        try {
            geradorDeEstoque.geraEstoqueDe(nota);
            atualizaLote(t);
            addNoBanco(nota);
            efetivaPedidoAFornecedores();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void efetivaPedidoAFornecedores() throws ConstraintViolationException, DadoInvalidoException {
        if (pedidoAFornecedores != null) {
            pedidoAFornecedores.efetiva();
            atualizaPedido.atualiza(pedidoAFornecedores);
        }
    }

    /**
     * Calcula a cotacao inicial ao abrir a janela de cotacoes
     */
    public void calculaCotacaoInicial() {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("cotacaoVal");
        if (notaRecebida.getTotalEmDinheiro().compareTo(getTotalConvertidoRecebido()) > 0) {
            for (ValorPorCotacaoBV c : cotacoes) {
                if (c.getCotacao().getConta().getMoeda().equals(configuracao.getMoedaPadrao())) {
                    c.setValorAReceber(notaRecebida.getTotalEmDinheiro());
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
            c.setTotal(notaRecebida.getTotalEmDinheiro());
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
        geraBoletoDeCartaoAVista();
    }

    // -------------- Fim Operações para criação da entidade ------------------
    // ---------------------- Forma de Recebimento ----------------------------
    private void calculaTotaisFormaDeRecebimento() {
        FormaDeRecebimento formaDeRecebimento = notaRecebida.getFormaDeRecebimento();
        if ((formaDeRecebimento.getPorcentagemDeEntrada() != null && formaDeRecebimento.getPorcentagemDeEntrada().compareTo(BigDecimal.ZERO) > 0)
                && notaRecebida.getTotalItens().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal cem = new BigDecimal(100);
            BigDecimal p = formaDeRecebimento.getPorcentagemDeEntrada().divide(cem, 2, BigDecimal.ROUND_UP);
            BigDecimal resultado = p.multiply(notaRecebida.getTotalNota());
            incluiValorDeFormaDeRecebimento(resultado);
        }
    }

    private void incluiValorDeFormaDeRecebimento(BigDecimal resultado) {
        switch (notaRecebida.getFormaDeRecebimento().getFormaPadraoDeEntrada()) {
            case DINHEIRO:
                notaRecebida.setTotalEmDinheiro(resultado);
                break;
            case CREDITO:
                //valoresAVista.setCredito(resultado);
                break;
            case CHEQUE:
//                valoresAVista.setCheque(resultado);
                break;
            case A_FATURAR:
                notaRecebida.setAFaturar(resultado);
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
            notaRecebida.adiciona(itemRecebido);
            limparItemDeNota();

            recalculaValores();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemRecebidoSelecionado != null) {
                notaRecebida.atualiza(itemRecebidoSelecionado, itemRecebido.construirComId());
                limparItemDeNota();
                recalculaValores();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemRecebidoSelecionado != null) {
            notaRecebida.remove(itemRecebidoSelecionado);
            limparItemDeNota();
            recalculaValores();
        }
    }

    public void limparItemDeNota() {
        itemRecebido = new ItemDeNotaBV();
        itemRecebidoSelecionado = null;
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
            if (notaRecebida.getFormaDeRecebimento().getConta() != null && cotacaoDeTitulo == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Deve_existir_cotacao_na_conta_bancaria") + ": " + notaRecebida.getFormaDeRecebimento().getConta().getNome());
            }
            if (notaRecebida.getAFaturar() != null && (notaRecebida.getAFaturar().compareTo(BigDecimal.ZERO) > 0
                    || getTotalParcelas().compareTo(BigDecimal.ZERO) > 0)) {
                Integer numParcelas = notaRecebida.getNumeroParcelas(); //Número de cobranca
                TipoPeriodicidade tipoPeridiocidade = notaRecebida.getFormaDeRecebimento().getTipoPeriodicidade();
                Integer periodicidade = notaRecebida.getFormaDeRecebimento().getPeriodicidade();

                if (numParcelas != null && numParcelas > 0) {

                    BigDecimal soma = notaRecebida.getAFaturar().add(getTotalParcelas());
                    Money m = Money.valueOf(soma.toString(), "USD");
                    Money[] distribute = m.distribute(numParcelas);

                    // Busca o primeiro vencimento das cobranca
                    Date vencimento = new DateUtil().getPeriodicidadeCalculada(new Date(), tipoPeridiocidade, periodicidade);

                    // Busca a conta bancária para caso a geração for de título.
                    Conta conta = null;
                    if (notaRecebida.getFormaDeRecebimento().getFormaPadraoDeParcela() == ModalidadeDeCobranca.TITULO) {
                        conta = notaRecebida.getFormaDeRecebimento().getConta();
                    }

                    cobrancas = new ArrayList<>();
                    for (int i = 0; i < numParcelas; i++) {
                        cobrancas.add(new CobrancaBuilder().comID(getIdParcela()).comValor(distribute[i].getAmount())
                                .comVencimento(vencimento).comDias(getDiasDeVencimento(vencimento)).comCotacao(cotacaoDeTitulo != null ? cotacaoDeTitulo : cotacao).comEmissao(notaRecebida.getEmissao())
                                .comTipoFormaDeRecebimentoParcela(notaRecebida.getFormaDeRecebimento().getFormaPadraoDeParcela()).comCodigoTransacao("000000")
                                .comOperacaoFinanceira(notaRecebida.getOperacao().getOperacaoFinanceira()).comCartao(notaRecebida.getFormaDeRecebimento().getCartao())
                                .comSituacaoDeCartao(SituacaoDeCartao.ABERTO).comSituacaoDeCheque(EstadoDeCheque.ABERTO).comPessoa(notaRecebida.getPessoa())
                                .comEntrada(false).comTipoLancamento(TipoLancamento.EMITIDA).comSituacaoDeCobranca(SituacaoDeCobranca.ABERTO)
                                .comFilial(notaRecebida.getFilial()).comParcela(i + 1).construir());
                        vencimento = new DateUtil().getPeriodicidadeCalculada(vencimento, tipoPeridiocidade, periodicidade);
                    }

                    recalculaValorAFaturar();
                }
            } else {
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
            cheque.setOperacaoFinanceira(notaRecebida.getOperacao().getOperacaoFinanceira());
            Cheque c = cheque.construirComID();

            notaRecebida.adiciona(c); //Adiciona cheque a lista
            limparChequeEntrada(); //Limpa cheque
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateChequeEntrada() {
        try {
            if (chequeSelecionado != null) {
                Cheque c = cheque.construirComID(); //Constroi cheque
                notaRecebida.atualiza(chequeSelecionado, c); //Atualiza cheque na lsita
                limparChequeEntrada(); //Limpa cheque
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteChequeEntrada() {
        if (chequeSelecionado != null) {
            notaRecebida.remove(chequeSelecionado); //Remove cheque
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
                    p.setCartao(notaRecebida.getFormaDeRecebimento().getCartao());
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
                    notaRecebida.setOperacao((Operacao) obj);
                }
            } else if (obj instanceof Pessoa) {
                notaRecebida.setPessoa((Pessoa) obj);
            } else if (obj instanceof ListaDePreco) {
                notaRecebida.setListaDePreco((ListaDePreco) obj);
            } else if (obj instanceof Item) {
                itemRecebido.setItem((Item) obj);
                atribuiItemASessao();
            } else if (obj instanceof FormaDeRecebimento) {
                FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) obj;
                notaRecebida.setFormaDeRecebimento(formaDeRecebimento);
                calculaTotaisFormaDeRecebimento();
                recalculaValores();
                buscaCotacaoDeTitulo(formaDeRecebimento);
            } else if (obj instanceof Banco && "detbanco-search".equals(idComponent)) {
                cheque.setBanco((Banco) obj);
            } else if (obj instanceof Banco && "bancoParcelas-search".equals(idComponent)) {
                cobrancaBV.setBanco((Banco) obj);
            } else if (obj instanceof PedidoAFornecedores && "importaPedidoAFornecedor-btn".equals(idComponent)) {
                importa((PedidoAFornecedores) obj);
            } else if (obj instanceof Cartao) {
                boletoDeCartao.setCartao((Cartao) obj);
            } else if (obj instanceof NotaRecebida) {
                importaItensDe((NotaRecebida) obj);
            } else if (obj instanceof List && "neQuantidade-btn".equals(idComponent)) {
                List<QuantidadeDeItemPorDeposito> lista = (List<QuantidadeDeItemPorDeposito>) event.getObject();
                itemRecebido.setListaDeQuantidade(lista);
                itemRecebido.setQuantidade(lista.stream().map(QuantidadeDeItemPorDeposito::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add));
                buscouDeposito = true;
            } else if (obj instanceof List && "exibePedidoAFornecedores-btn".equals(idComponent)) {
                List<ItemDePedidoBV> lista = (List<ItemDePedidoBV>) event.getObject();
                importaItensDe(lista);
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
            itemRecebido.setListaDeQuantidade(Arrays.asList(new QuantidadeDeItemPorDeposito(null, new SaldoDeEstoque(null, configuracaoEstoque.getDepositoPadrao(), null), itemRecebido.getQuantidade())));
        } else {
            buscouDeposito = false;
        }
    }

    private void importa(PedidoAFornecedores pedido) throws DadoInvalidoException {
        pedidoAFornecedores = pedido;
        SessionUtil.put(pedido, "pedidoAFornecedores", FacesContext.getCurrentInstance());
        SessionUtil.put(pedido.getOperacao().getTipoOperacao(), "tipoOperacao", FacesContext.getCurrentInstance());
        RequestContext.getCurrentInstance().execute("document.getElementById(\"conteudo:ne:exibePedidoAFornecedores-btn\").click();");
    }

    private void importaItensDe(List<ItemDePedidoBV> lista) {
        if (!lista.isEmpty()) {
            for (ItemDePedidoBV i : lista) {
                itemRecebido.setItem(i.getItem());
                itemRecebido.setUnitario(i.getValorUnitario());
                itemRecebido.setListaDeQuantidade(i.getListaDeQuantidade());
                itemRecebido.setQuantidade(lista.stream().map((q) -> q.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add));
                addItemNaLista();
            }
            importaParcelasDe(pedidoAFornecedores);
        }
    }

    private void importaParcelasDe(PedidoAFornecedores pedidoAFornecedores) {
        try {
            for (ParcelaDePedido p : pedidoAFornecedores.getParcelaDePedido()) {
                cobrancaBV = new CobrancaBV();
                cobrancaBV.setEmissao(pedidoAFornecedores.getEmissao());
                cobrancaBV.setValor(p.getValor());
                cobrancaBV.setVencimento(p.getVencimento());
                cobrancaBV.setCotacao(cotacaoDAO.porMoeda(pedidoAFornecedores.getMoeda()).porCotacaoEmpresa().naUltimaEmissao(new Date()).resultado());
                cobrancaBV.setModalidadeDeCobranca(ModalidadeDeCobranca.TITULO);
                cobrancaBV.setTipoLancamento(TipoLancamento.RECEBIDA);
                cobrancaBV.setSituacaoDeCobranca(SituacaoDeCobranca.ABERTO);
                cobrancaBV.setPessoa(pedidoAFornecedores.getPessoa());
                cobrancaBV.setOperacaoFinanceira(pedidoAFornecedores.getOperacao().getOperacaoFinanceira());
                cobrancaBV.setMoeda(pedidoAFornecedores.getMoeda());
                cobrancaBV.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
                cobrancas.add(cobrancaBV);
            }
            notaRecebida.setPessoa(pedidoAFornecedores.getPessoa());
            notaRecebida.setOperacao(pedidoAFornecedores.getOperacao());
            notaRecebida.setPedidoAFornecedores(pedidoAFornecedores);
            notaRecebida.setFormaDeRecebimento(pedidoAFornecedores.getFormaDeRecebimento());
            notaRecebida.setAcrescimo(pedidoAFornecedores.getAcrescimo());
            notaRecebida.setDesconto(pedidoAFornecedores.getDesconto());
            notaRecebida.setDespesaCobranca(pedidoAFornecedores.getDespesaCobranca());
            notaRecebida.setFrete(pedidoAFornecedores.getFrete());
            notaRecebida.setTotalEmDinheiro(pedidoAFornecedores.getTotalEmDinheiro());
            recalculaValores();
            RequestContext.getCurrentInstance().update("conteudo");
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void importaItensDe(NotaRecebida nota) throws DadoInvalidoException {
//        for (ItemDeNota ie : (nota).getItens()) {
//            itemRecebido.setItem(ie.getItem());
//            itemRecebido.setUnitario(ie.getUnitario());
//            for (Estoque estoqueDeItemDeNota : ie.getEstoques()) {
//                EstoqueBV ebv = new EstoqueBV(estoqueDeItemDeNota);
//                itemRecebido.getEstoque().add(ebv.construir());
//            }
//            addItemNaLista();
//        }
    }

    public void atribuiItemASessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.item.token");
        session.setAttribute("onesystem.item.token", itemRecebido.getItem());
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
        this.itemRecebidoSelecionado = (ItemDeNota) event.getObject();
        this.itemRecebido = new ItemDeNotaBV(itemRecebidoSelecionado);
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
    public void validaLoteItem() {
        //Verifica se item possui lote
        for (LoteItem lote : loteItemService.buscarLoteItemPorItem(itemRecebido.getItem())) {
            if (lote.getNumeroDoLote().equals(loteItemBV.getNumeroDoLote())) {
                loteItemBV = new LoteItemBV(lote);
                InfoMessage.print(new BundleUtil().getLabel("Lote_Selecionado") + " : " + lote.getNumeroDoLote() + " - "
                        + new BundleUtil().getLabel("Data_De_Fabricacao") + ": " + lote.getFabricacaoFormatada() + " - "
                        + new BundleUtil().getLabel("Data_De_Validade") + ": " + lote.getValidadeFormatada());
                return;
            }
        }
        //Se o lote nao estiver cadastrado abre o dialogo pedindo se quer cadastrar
        RequestContext.getCurrentInstance().execute("PF('incluirLoteDeItemDialog').show()");
    }

    public void atualizaLote(NotaRecebida nota) {
        for (ItemDeNota item : nota.getItens()) {
            if (item.getItem().getLoteItem().equals(null)) {
                return;
            } else {//usando quando Adicionado uma Nota Nova
              //  loteItemService.atualizaSaldoLote(item.getItem(), new LoteItemBV(item.get), item.getQuantidade(), operacaoDeEstoqueService.buscarOperacaoFisicaPor(e.getOperacao()));
            }
        }
    }

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
        BigDecimal total = notaRecebida.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pAcrescimo = notaRecebida.getPorcentagemAcrescimo() == null ? BigDecimal.ZERO : notaRecebida.getPorcentagemAcrescimo();
            BigDecimal pDesconto = notaRecebida.getPorcentagemDesconto() == null ? BigDecimal.ZERO : notaRecebida.getPorcentagemDesconto();
            BigDecimal acrescimo;
            BigDecimal desconto;
            BigDecimal cem = new BigDecimal(100);

            if (pAcrescimo.compareTo(BigDecimal.ZERO) > 0) {
                acrescimo = (pAcrescimo.multiply(total)).divide(cem, 2, BigDecimal.ROUND_UP);
                notaRecebida.setAcrescimo(acrescimo);
            } else {
                notaRecebida.setAcrescimo(BigDecimal.ZERO);
            }
            if (pDesconto.compareTo(BigDecimal.ZERO) > 0) {
                desconto = (pDesconto.multiply((total))).divide(cem, 2, BigDecimal.ROUND_UP);
                notaRecebida.setDesconto(desconto);
            } else {
                notaRecebida.setDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(notaRecebida.getTotalNota());
        }
    }

    /**
     * Calcula a porcentagem de acréscimo e desconto após informar um dos campos
     * de valor de acréscimo e desconto.
     */
    public void calculaPorcentagemAcrescimoEDesconto() {
        BigDecimal total = notaRecebida.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal acrescimo = notaRecebida.getAcrescimo() == null ? BigDecimal.ZERO : notaRecebida.getAcrescimo();
            BigDecimal desconto = notaRecebida.getDesconto() == null ? BigDecimal.ZERO : notaRecebida.getDesconto();
            BigDecimal pAcrescimo;
            BigDecimal pDesconto;
            BigDecimal cem = new BigDecimal(100);

            if (acrescimo.compareTo(BigDecimal.ZERO) > 0) {
                pAcrescimo = (acrescimo.multiply(cem)).divide(total, 2, BigDecimal.ROUND_UP);
                notaRecebida.setPorcentagemAcrescimo(pAcrescimo);
            } else {
                notaRecebida.setPorcentagemAcrescimo(BigDecimal.ZERO);
            }
            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
                pDesconto = (desconto.multiply(cem)).divide(((total)), 2, BigDecimal.ROUND_UP);
                notaRecebida.setPorcentagemDesconto(pDesconto);
            } else {
                notaRecebida.setPorcentagemDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(notaRecebida.getTotalNota());
        }
    }

    public void calculaValoresTotais() {
        incluiValorDeFormaDeRecebimento(notaRecebida.getTotalNota());
    }

    public void recalculaValores() {
        if (cobrancas.isEmpty()) {
            recalculaValorAFaturar();
        } else {
            recalculaValorAFaturar();
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
            boletoDeCartao.setEmissao(notaRecebida.getEmissao());
            boletoDeCartao.setPessoa(notaRecebida.getPessoa());
            boletoDeCartao.setOperacaoFinanceira(notaRecebida.getOperacao().getOperacaoFinanceira());
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

        BigDecimal chequeTotal = notaRecebida.getTotalChequeDeEntrada() == null ? BigDecimal.ZERO : notaRecebida.getTotalChequeDeEntrada();
        BigDecimal cartao = boletoDeCartao.getValor() == null ? BigDecimal.ZERO : boletoDeCartao.getValor();
        BigDecimal credito = creditoBV.getValor() == null ? BigDecimal.ZERO : creditoBV.getValor();
        BigDecimal dinheiro = notaRecebida.getTotalEmDinheiro() == null ? BigDecimal.ZERO : notaRecebida.getTotalEmDinheiro();
        BigDecimal totalParcelas = getTotalParcelas() == null ? BigDecimal.ZERO : getTotalParcelas();
        BigDecimal soma = chequeTotal.add(cartao).add(credito).add(totalParcelas).add(dinheiro);

        notaRecebida.setAFaturar(notaRecebida.getTotalNota().subtract(soma));
    }

    // ---------------- Fim Outras Operações da Janela ------------------------
    //----------------------- Getter Personalizados ---------------------------
    public Integer getDiasDeVencimento(Date vencimento) {
        LocalDate venc = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long dias = LocalDate.now().until(venc, ChronoUnit.DAYS);
        return dias.intValue();
    }

    public String getValorRestante() {
        BigDecimal total = notaRecebida.getTotalEmDinheiro();
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
    public NotaRecebida getNotaRecebidaSelecionada() {
        return notaRecebidaSelecionada;
    }

    public void setNotaSelecionada(NotaRecebida notaRecebidaSelecionada) {
        this.notaRecebidaSelecionada = notaRecebidaSelecionada;
    }

    public NotaRecebidaBV getNotaRecebida() {
        return notaRecebida;
    }

    public List<ModalidadeDeCobranca> getTiposDeFormaDeRecebimentoParcela() {
        List<ModalidadeDeCobranca> forma = new ArrayList<>();
        if (notaRecebida.getFormaDeRecebimento().isParcelaEmCartao()) {
            forma.add(ModalidadeDeCobranca.CARTAO);
        }
        if (notaRecebida.getFormaDeRecebimento().isParcelaEmCheque()) {
            forma.add(ModalidadeDeCobranca.CHEQUE);
        }
        if (notaRecebida.getFormaDeRecebimento().isParcelaEmConta()) {
            forma.add(ModalidadeDeCobranca.TITULO);
        }
        return forma;
    }

    public void setNota(NotaRecebidaBV notaRecebida) {
        this.notaRecebida = notaRecebida;
    }

    public ItemDeNotaBV getItemRecebido() {
        return itemRecebido;
    }

    public void setItemRecebido(ItemDeNotaBV itemRecebido) {
        this.itemRecebido = itemRecebido;
    }

    public ItemDeNota getItemRecebidoSelecionado() {
        return itemRecebidoSelecionado;
    }

    public void setItemRecebidoSelecionado(ItemDeNota itemRecebidoSelecionado) {
        this.itemRecebidoSelecionado = itemRecebidoSelecionado;
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

    public LoteItemBV getLoteItemBV() {
        return loteItemBV;
    }

    public void setLoteItemBV(LoteItemBV loteItemBV) {
        this.loteItemBV = loteItemBV;
    }

    //------------------- Fim Getters and Setters -----------------------------
}
