/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.domain.builder.ParcelaBuilder;
import br.com.onesystem.exception.CurrencyMissmatchException;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.CotacaoValores;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.DateUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.Money;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.builder.EstoqueBV;
import br.com.onesystem.war.builder.ValoresAVistaBV;
import br.com.onesystem.war.builder.ItemEmitidoBV;
import br.com.onesystem.war.builder.NotaEmitidaBV;
import br.com.onesystem.war.builder.ParcelaBV;
import br.com.onesystem.war.builder.QuantidadeDeItemBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
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
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@ManagedBean
@ViewScoped
public class NotaEmitidaView extends BasicMBImpl<NotaEmitida> implements Serializable {

    private ValoresAVistaBV valoresAVista;
    private NotaEmitida notaEmitidaSelecionada;
    private NotaEmitidaBV notaEmitida;
    private ItemEmitidoBV itemEmitido;
    private ItemEmitido itemEmitidoSelecionado;
    private EstoqueBV estoqueBV;
    private Configuracao configuracao;
    private CotacaoValores cotacaoValoresSelecionado;
    private List<Cotacao> cotacaoLista;
    private List<CotacaoValores> cotacoes;
    private List<ParcelaBV> parcelas;
    private ParcelaBV parcelaSelecionada;
    private ParcelaBV parcelaBV;
    private NotaEmitida novoRegistroNE;
    private Cotacao cotacao;

    //Variáveis para criação de Cheques
    private ChequeBV cheque;
    private Cheque chequeSelecionado;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService configuracaoService;

    @ManagedProperty("#{cotacaoService}")
    private CotacaoService service;

    // ---------------------- Inicializa Janela -------------------------------
    @PostConstruct
    public void init() {
        iniciarConfiguracoes();
        limparJanela();
        limpaSessao();

    }

    private void iniciarConfiguracoes() {
        try {
            configuracao = configuracaoService.buscar();
            cotacao = new CotacaoDAO().buscarCotacoes().porMoeda(configuracao.getMoedaPadrao()).naMaiorEmissao(new Date()).resultado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void limparJanela() {
        novoRegistroNE = null;
        notaEmitida = new NotaEmitidaBV();
        itemEmitido = new ItemEmitidoBV();
        notaEmitida.setItensEmitidos(new ArrayList<ItemEmitido>());
        valoresAVista = new ValoresAVistaBV();
        parcelas = new ArrayList<ParcelaBV>();
        notaEmitidaSelecionada = null;
        cheque = new ChequeBV();
        inicializaCotacoes();
        limparChequeEntrada();
        parcelaBV = new ParcelaBV();
    }

    private void limpaSessao() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        if (session.getAttribute("onesystem.item.token") != null) {
            session.removeAttribute("onesystem.item.token");
        }
    }

    private void inicializaCotacoes() {
        cotacaoLista = service.buscarCotacoesDoDiaAtual();
        cotacoes = new ArrayList<CotacaoValores>();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new CotacaoValores(c, null, null, null));
        }
    }

    // --------------------- Fim Inicializa Janela ----------------------------
    // -------------- Operações para criação da entidade ----------------------
    /**
     * Prepara valor de recebimento e insere a entidade no banco de dados
     */
    public void add() {
        try {
            preparaInclusaoDeRecebimentoDeValores();
            new AdicionaDAO<NotaEmitida>().adiciona(novoRegistroNE);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        } catch (ConstraintViolationException cpe) {
            ErrorMessage.print(cpe.toString());
        }
    }

    /**
     * Gera a entidade com os dados de recebimento, parcelas, itens e cotações.
     */
    public void finalizar() {
        try {
            notaEmitida.setValoresAVista(valoresAVista.construir());
            novoRegistroNE = notaEmitida.construir();

            // Se existir parcela, gera as parcelas e adiciona na nota emitida.
            if (!parcelas.isEmpty()) {
                preparaInclusaoDeParcelas();
            }

            // Gera os itens emitidos e adiciona na nota emitida;
            preparaInclusaoDeItemEmitido();

            // Se existir valor em dinheiro abre a janela de cotações.
            if (valoresAVista.getDinheiro().compareTo(BigDecimal.ZERO) > 0) {
                addTotalCotacoes(); // abre a janela de cotações
            } else {
                add();
            }

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    /**
     * Gera as parcelas para persistência.
     *
     * @throws br.com.onesystem.exception.DadoInvalidoException
     */
    public void preparaInclusaoDeParcelas() throws DadoInvalidoException {
        List<BoletoDeCartao> cartoes = new ArrayList<BoletoDeCartao>();
        List<Cheque> cheques = new ArrayList<Cheque>();
        List<Titulo> titulos = new ArrayList<Titulo>();

        //Gera as parcelas de acordo a sua modalidade.
        for (ParcelaBV p : parcelas) {
            switch (p.getTipoFormaDeRecebimentoParcela()) {
                case CARTAO:
                    cartoes.add(p.construirBoletoDeCartao(novoRegistroNE));
                    break;
                case CHEQUE:
                    cheques.add(p.construirCheque(novoRegistroNE));
                    break;
                case TITULO:
                    titulos.add(p.construirTitulo(novoRegistroNE));
                    break;
                default:
                    break;
            }
        }

        // Inclui as parcelas dentro da nota emitida.
        novoRegistroNE.setCartoes(cartoes);
        novoRegistroNE.setCheques(cheques);
        novoRegistroNE.setTitulos(titulos);
    }

    private List<Estoque> criarBaixaDeEstoque(List<QuantidadeDeItemBV> lista) throws DadoInvalidoException {
        List<Estoque> estoquesBV = new ArrayList<Estoque>();
        for (QuantidadeDeItemBV q : lista) {
            estoqueBV = new EstoqueBV();
            estoqueBV.setDeposito(q.getSaldoDeEstoque().getDeposito());
            estoqueBV.setQuantidade(q.getQuantidade());
            estoqueBV.setTipo(OperacaoFisica.SAIDA);
            estoqueBV.setItem(itemEmitido.getItem());
            estoquesBV.add(estoqueBV.construir());
        }
        return estoquesBV;
    }

    /**
     * Busca a moeda padrão e abre a janela de cotações com o valor restante na
     * cotação de cada moeda.
     */
    public void addTotalCotacoes() {
        valoresAVista.setMoeda(configuracao.getMoedaPadrao());
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("cotacaoVal");
        for (CotacaoValores c : cotacoes) {
            c.setTotal(valoresAVista.getDinheiro());
            c.setTotalConvertidoRecebido(getTotalConvertidoRecebido());
        }
        rc.execute("PF('cotacaoVal').show()");
    }

    /**
     * Prepara inclusão do ItemEmitido na tabela e prepara a inclusão dos dados
     * no estoque.
     *
     * Obs: Essa preparação deve excluir o id de cada entidade para que o
     * hibernate entenda que é uma inclusão de objeto, além disso deve informar
     * a entidade PAI no cadastro para que o hibernate faça o relacionamento
     * automático das entidades.
     */
    private void preparaInclusaoDeItemEmitido() throws ConstraintViolationException, DadoInvalidoException {
        for (ItemEmitido ie : novoRegistroNE.getItensEmitidos()) {
            ie.preparaInclusao(novoRegistroNE); // Exclui ID de ItemEmitido e informa a Nota Emitida.
            for (Estoque estoque : ie.getEstoque()) {
                estoque.preparaInclusaoDe(ie); // Exclui ID do Estoque e informa o Item Emitido.
            }
        }
    }

    /**
     * Gera as baixas para o recebimento do valor a vista.
     *
     * @throws br.com.onesystem.exception.DadoInvalidoException
     */
    public void preparaInclusaoDeRecebimentoDeValores() throws DadoInvalidoException {
        if (valoresAVista.getDinheiro().compareTo(BigDecimal.ZERO) > 0) {
            List<Baixa> baixas = new ArrayList<Baixa>();
            for (CotacaoValores c : cotacoes) {
                if (c.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
                    Baixa baixa = new BaixaBuilder().cancelada(false)
                            .comConta(c.getCotacao().getConta()).comDesconto(novoRegistroNE.getValoresAVista().getDesconto()).comEmissao(novoRegistroNE.getEmissao())
                            .comNaturezaFinanceira(novoRegistroNE.getOperacao().getOperacaoFinanceira())
                            .comPessoa(novoRegistroNE.getPessoa()).comReceita(novoRegistroNE.getOperacao().getVendaAVista()).
                            comTotal(getTotalNota()).comValor(getTotalNota()).comNotaEmitida(novoRegistroNE).construir();
                }
            }
            novoRegistroNE.setBaixas(baixas);
        }
    }

    @Override
    public void buscaPorId() {
        Long id = notaEmitida.getId();
        if (id != null) {
            try {
                NotaEmitidaDAO dao = new NotaEmitidaDAO();
                NotaEmitida ne = dao.buscarNotaEmitidaW().porId(id).resultado();
                notaEmitidaSelecionada = ne;
                notaEmitida = new NotaEmitidaBV(notaEmitidaSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                notaEmitida.setId(id);
                die.print();
            }
        }
    }

    // -------------- Fim Operações para criação da entidade ------------------
    // ---------------------- Forma de Recebimento ----------------------------
    private void calculaTotaisFormaDeRecebimento() {
        FormaDeRecebimento formaDeRecebimento = notaEmitida.getFormaDeRecebimento();
        if (formaDeRecebimento.getPorcentagemDeEntrada().compareTo(BigDecimal.ZERO) > 0
                && getTotalItens().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal cem = new BigDecimal(100);
            BigDecimal p = formaDeRecebimento.getPorcentagemDeEntrada().divide(cem, 2, BigDecimal.ROUND_UP);
            BigDecimal resultado = p.multiply(getTotalNota());
            incluiValorDeFormaDeRecebimento(resultado);
        }
    }

    private void incluiValorDeFormaDeRecebimento(BigDecimal resultado) {
        switch (notaEmitida.getFormaDeRecebimento().getFormaPadraoDeEntrada()) {
            case DINHEIRO:
                valoresAVista.setDinheiro(resultado);
                break;
            case CREDITO:
                valoresAVista.setCredito(resultado);
                break;
            case CHEQUE:
//                valoresAVista.setCheque(resultado);
                break;
            case A_FATURAR:
                valoresAVista.setAFaturar(resultado);
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
            itemEmitido.setId(getCodigoItem());
            notaEmitida.getItensEmitidos().add(itemEmitido.construirComId());
            limparItemEmitido();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemEmitidoSelecionado != null) {
                notaEmitida.getItensEmitidos().set(notaEmitida.getItensEmitidos().indexOf(itemEmitidoSelecionado),
                        itemEmitido.construirComId());
                limparItemEmitido();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemEmitidoSelecionado != null) {
            notaEmitida.getItensEmitidos().remove(itemEmitidoSelecionado);
            limparItemEmitido();
        }
    }

    public void limparItemEmitido() {
        itemEmitido = new ItemEmitidoBV();
        itemEmitidoSelecionado = null;
    }

    public String getTotalItensFormatado() {
        if (notaEmitida.getItensEmitidos().isEmpty()) {
            return "";
        } else {
            return configuracao.getMoedaPadrao().getSigla() + " " + NumberFormat.getNumberInstance().format(getTotalItens());
        }
    }

    private BigDecimal getTotalItens() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemEmitido i : notaEmitida.getItensEmitidos()) {
            total = total.add(i.getTotal());
        }
        return total;
    }
    // -------------------------- Fim Itens -----------------------------------
    // --------------------------- Parcelas -----------------------------------

    /**
     * Método responsável por abrir o diálogo de detalhamento das parcelas.
     */
    public void detalharParcela() {
        parcelaBV = new ParcelaBV(parcelaSelecionada);
        RequestContext req = RequestContext.getCurrentInstance();
        if (parcelaSelecionada.getTipoFormaDeRecebimentoParcela() == TipoFormaDeRecebimentoParcela.CHEQUE) {
            req.execute("PF('detalheChequeParcela').show()");
            req.update("conteudo:panelDetCheParcela");
        } else if (parcelaSelecionada.getTipoFormaDeRecebimentoParcela() == TipoFormaDeRecebimentoParcela.CARTAO) {

            req.execute("PF('detalheCartaoParcela').show()");
            req.update("conteudo:panelDetCartaoPar");
        }
    }

    /**
     * Método que criará as parcelas automaticamente ao informar e sair do campo
     * número de parcelas.
     */
    public void criaParcelas() {
        try {
            if (valoresAVista.getAFaturar() != null && (valoresAVista.getAFaturar().compareTo(BigDecimal.ZERO) > 0
                    || getTotalParcelas().compareTo(BigDecimal.ZERO) > 0)) {
                Integer numParcelas = valoresAVista.getParcelas(); //Número de parcelas
                TipoPeriodicidade tipoPeridiocidade = notaEmitida.getFormaDeRecebimento().getTipoPeriodicidade();
                Integer periodicidade = notaEmitida.getFormaDeRecebimento().getPeriodicidade();

                if (numParcelas != null && numParcelas > 0) {

                    BigDecimal soma = valoresAVista.getAFaturar().add(getTotalParcelas());
                    Money m = Money.valueOf(soma.toString(), "USD");
                    Money[] distribute = m.distribute(numParcelas);

                    // Busca o primeiro vencimento das parcelas
                    Date vencimento = new DateUtil().getPeriodicidadeCalculada(new Date(), tipoPeridiocidade, periodicidade);

                    parcelas = new ArrayList<>();
                    for (int i = 0; i < numParcelas; i++) {
                        parcelas.add(new ParcelaBuilder().comID(getIdParcela()).comValor(distribute[i].getAmount())
                                .comVencimento(vencimento).comDias(getDiasDeVencimento(vencimento)).comCotacao(cotacao).comEmissao(notaEmitida.getEmissao())
                                .comTipoFormaDeRecebimentoParcela(notaEmitida.getFormaDeRecebimento().getFormaPadraoDeParcela()).construir());
                        vencimento = new DateUtil().getPeriodicidadeCalculada(vencimento, tipoPeridiocidade, periodicidade);
                    }

                    recalculaValorAFaturar();
                }
            } else {
                ErrorMessage.print(new BundleUtil().getMessage("Nao_Existe_Valor_A_Faturar"));
            }
        } catch (CurrencyMissmatchException cme) {
            ErrorMessage.print("Erro ao calcular o valor das parcelas.");
        }
    }

    /**
     * Adiciona o cheque dentro da lista de cheques na janela
     * detalheChequeEntrada.
     */
    public void addChequeEntrada() {
        try {
            preparaChequeEntrada();
            Cheque c = cheque.construirComID();
            valoresAVista.getCheques().add(c);
            limparChequeEntrada();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateChequeEntrada() {
        try {
            if (chequeSelecionado != null) {
                Cheque c = cheque.construirComID();
                valoresAVista.getCheques().set(valoresAVista.getCheques().indexOf(chequeSelecionado), c);
                limparChequeEntrada();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteChequeEntrada() {
        if (chequeSelecionado != null) {
            valoresAVista.getCheques().remove(valoresAVista.getCheques().indexOf(chequeSelecionado));
        }
        limparChequeEntrada();
    }

    public void limparChequeEntrada() {
        chequeSelecionado = null;
        cheque = new ChequeBV();
        cheque.setCotacao(cotacao);
    }

    /**
     * Prepara cheque para ser adicionado.
     */
    private void preparaChequeEntrada() {
        cheque.setId(getIdCheque());
        cheque.setTipoSituacao(SituacaoDeCheque.ABERTO);
    }

    // ------------------------- Fim Parcelas ---------------------------------
    // ----------------------------- Selecao ----------------------------------
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();

        if (obj instanceof Operacao) {
            notaEmitida.setOperacao((Operacao) obj);
        } else if (obj instanceof Pessoa) {
            notaEmitida.setPessoa((Pessoa) obj);
        } else if (obj instanceof ListaDePreco) {
            notaEmitida.setListaDePreco((ListaDePreco) obj);
        } else if (obj instanceof Item) {
            itemEmitido.setItem((Item) obj);
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            session.setAttribute("onesystem.item.token", itemEmitido.getItem());
        } else if (obj instanceof FormaDeRecebimento) {
            FormaDeRecebimento formaDeRecebimento = (FormaDeRecebimento) obj;
            notaEmitida.setFormaDeRecebimento(formaDeRecebimento);
            calculaTotaisFormaDeRecebimento();
        } else if (obj instanceof Banco) {
            cheque.setBanco((Banco) obj);
        }
    }

    public void selecionaChequeDeEntrada(SelectEvent event) {
        chequeSelecionado = (Cheque) event.getObject();
        cheque = new ChequeBV(chequeSelecionado);
    }

    public void selecionaCartao(SelectEvent event) {
        parcelaBV.setCartao((Cartao) event.getObject());
    }

    public void selecionaQuantidadeDeItemBV(SelectEvent event) {
        try {
            List<QuantidadeDeItemBV> lista = (List<QuantidadeDeItemBV>) event.getObject();
            itemEmitido.setEstoque(criarBaixaDeEstoque(lista));
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void selecionaItemEmitido(SelectEvent event) {
        this.itemEmitidoSelecionado = (ItemEmitido) event.getObject();
        this.itemEmitido = new ItemEmitidoBV(itemEmitidoSelecionado);
    }

    public void selecionarBanco(SelectEvent event) {
        Banco banco = (Banco) event.getObject();
        parcelaBV.setBanco(banco);
    }

    public void selecionaChequeEntrada(SelectEvent event) {
        chequeSelecionado = (Cheque) event.getObject();
        cheque = new ChequeBV(chequeSelecionado);
    }

    // ----------------------------- Fim Selecao ------------------------------
    // ------------------ Outras Operações da Janela --------------------------
    public void addDetalheParcelaCartao() {
        try {
            parcelaBV.setSituacaoDeCartao(SituacaoDeCartao.ABERTO);
            parcelaBV.construirBoletoDeCartao(null); // Constroi Boleto de Cartão para validar.

            //Seleciona o cartao na parcela
            parcelaSelecionada.setCartao(parcelaBV.getCartao());
            parcelaSelecionada.setCodigoTransacao(parcelaBV.getCodigoTransacao());
            parcelas.set(parcelas.indexOf(parcelaSelecionada), parcelaSelecionada);
            parcelaBV = new ParcelaBV();
            parcelaSelecionada = null;
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void addDetalheParcelaCheque() {
        try {
            parcelaBV.setSituacaoDeCheque(SituacaoDeCheque.ABERTO);
            parcelaBV.construirCheque(null); // Constroi Cheque para validar.

            //Seleciona o cheque na parcela
            parcelaSelecionada.setBanco(parcelaBV.getBanco());
            parcelaSelecionada.setAgencia(parcelaBV.getAgencia());
            parcelaSelecionada.setNumeroCheque(parcelaBV.getNumeroCheque());
            parcelaSelecionada.setConta(parcelaBV.getConta());
            parcelaSelecionada.setEmitente(parcelaBV.getEmitente());
            parcelaSelecionada.setObservacao(parcelaBV.getObservacao());
            parcelas.set(parcelas.indexOf(parcelaSelecionada), parcelaSelecionada);
            parcelaBV = new ParcelaBV();
            parcelaSelecionada = null;
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    /**
     * Calcula o valor de acréscimo e desconto após informar um dos campos de
     * porcentagem de acréscimo e desconto.
     */
    public void calculaValorAcrescimoEDesconto() {
        BigDecimal total = getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pAcrescimo = valoresAVista.getPorcentagemAcrescimo() == null ? BigDecimal.ZERO : valoresAVista.getPorcentagemAcrescimo();
            BigDecimal pDesconto = valoresAVista.getPorcentagemDesconto() == null ? BigDecimal.ZERO : valoresAVista.getPorcentagemDesconto();
            BigDecimal acrescimo = BigDecimal.ZERO;
            BigDecimal desconto = BigDecimal.ZERO;
            BigDecimal cem = new BigDecimal(100);

            if (pAcrescimo.compareTo(BigDecimal.ZERO) > 0) {
                acrescimo = (pAcrescimo.multiply(total)).divide(cem, 2, BigDecimal.ROUND_UP);
                valoresAVista.setAcrescimo(acrescimo);
            } else {
                valoresAVista.setAcrescimo(BigDecimal.ZERO);
            }
            if (pDesconto.compareTo(BigDecimal.ZERO) > 0) {
                desconto = (pDesconto.multiply((total))).divide(cem, 2, BigDecimal.ROUND_UP);
                valoresAVista.setDesconto(desconto);
            } else {
                valoresAVista.setDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(getTotalNota());
        }
    }

    /**
     * Calcula a porcentagem de acréscimo e desconto após informar um dos campos
     * de valor de acréscimo e desconto.
     */
    public void calculaPorcentagemAcrescimoEDesconto() {
        BigDecimal total = getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal acrescimo = valoresAVista.getAcrescimo() == null ? BigDecimal.ZERO : valoresAVista.getAcrescimo();
            BigDecimal desconto = valoresAVista.getDesconto() == null ? BigDecimal.ZERO : valoresAVista.getDesconto();
            BigDecimal pAcrescimo = BigDecimal.ZERO;
            BigDecimal pDesconto = BigDecimal.ZERO;
            BigDecimal cem = new BigDecimal(100);

            if (acrescimo.compareTo(BigDecimal.ZERO) > 0) {
                pAcrescimo = (acrescimo.multiply(cem)).divide(total, 2, BigDecimal.ROUND_UP);
                valoresAVista.setPorcentagemAcrescimo(pAcrescimo);
            } else {
                valoresAVista.setPorcentagemAcrescimo(BigDecimal.ZERO);
            }
            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
                pDesconto = (desconto.multiply(cem)).divide(((total)), 2, BigDecimal.ROUND_UP);
                valoresAVista.setPorcentagemDesconto(pDesconto);
            } else {
                valoresAVista.setPorcentagemDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(getTotalNota());
        }
    }

    public void calculaValoresTotais() {
        incluiValorDeFormaDeRecebimento(getTotalNota());
    }

    public void recalculaValores() {
        if (parcelas.isEmpty()) {
            recalculaValorAFaturar();
        } else {
            recalculaValorAFaturar();
            criaParcelas();
        }
    }

    public void recalculaValorAFaturar() {

        BigDecimal cheque = getTotalCheque() == null ? BigDecimal.ZERO : getTotalCheque();
        BigDecimal cartao = valoresAVista.getBoletoDeCartao().getValor() == null ? BigDecimal.ZERO : valoresAVista.getBoletoDeCartao().getValor();
        BigDecimal credito = valoresAVista.getCredito() == null ? BigDecimal.ZERO : valoresAVista.getCredito();
        BigDecimal dinheiro = valoresAVista.getDinheiro() == null ? BigDecimal.ZERO : valoresAVista.getDinheiro();
        BigDecimal totalParcelas = getTotalParcelas() == null ? BigDecimal.ZERO : getTotalParcelas();
        BigDecimal soma = cheque.add(cartao).add(credito).add(totalParcelas).add(dinheiro);

        valoresAVista.setAFaturar(getTotalNota().subtract(soma));
    }

    // ---------------- Fim Outras Operações da Janela ------------------------
    //----------------------- Getter Personalizados ---------------------------
    public Integer getDiasDeVencimento(Date vencimento) {
        LocalDate venc = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long dias = LocalDate.now().until(venc, ChronoUnit.DAYS);
        return dias.intValue();
    }

    public BigDecimal getTotalNota() {
        BigDecimal acrescimo = valoresAVista.getAcrescimo() == null ? BigDecimal.ZERO : valoresAVista.getAcrescimo();
        BigDecimal frete = valoresAVista.getFrete() == null ? BigDecimal.ZERO : valoresAVista.getFrete();
        BigDecimal despesaCobranca = valoresAVista.getDespesaCobranca() == null ? BigDecimal.ZERO : valoresAVista.getDespesaCobranca();
        BigDecimal desconto = valoresAVista.getDesconto() == null ? BigDecimal.ZERO : valoresAVista.getDesconto();

        return getTotalItens().add(acrescimo.add(frete.add(despesaCobranca)).subtract(desconto));
    }

    public String getValorRestante() {
        BigDecimal total = valoresAVista.getDinheiro();
        BigDecimal valorAReceber = BigDecimal.ZERO;
        NumberFormat nf = NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal());

        for (CotacaoValores c : cotacoes) {
            valorAReceber = valorAReceber.add(c.getValorConvertidoRecebido());
        }

        if (total == null || total.subtract(valorAReceber).compareTo(BigDecimal.ZERO) < 0) {
            return nf.format(BigDecimal.ZERO);
        } else {
            return nf.format(total.subtract(valorAReceber));
        }
    }

    public String getGanhoDeCambio() {
        BigDecimal total = valoresAVista.getDinheiro();
        BigDecimal valorAReceber = BigDecimal.ZERO;
        for (CotacaoValores c : cotacoes) {
            valorAReceber = valorAReceber.add(c.getValorConvertidoRecebido());
        }
        return total == null ? NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(BigDecimal.ZERO)
                : total.subtract(valorAReceber).compareTo(BigDecimal.ZERO) < 0 ? NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(total.subtract(valorAReceber).multiply(new BigDecimal(-1)))
                : NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(BigDecimal.ZERO);
    }

    public BigDecimal getTotalConvertidoRecebido() {
        BigDecimal total = BigDecimal.ZERO;
        for (CotacaoValores c : cotacoes) {
            total = total.add(c.getValorConvertidoRecebido());
        }
        return total;
    }

    public BigDecimal getTotalParcelas() {
        BigDecimal totalParcela = BigDecimal.ZERO;
        for (ParcelaBV p : parcelas) {
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
        if (!parcelas.isEmpty()) {
            for (ParcelaBV p : parcelas) {
                if (p.getId() >= id) {
                    id = p.getId() + 1;
                }
            }
        }
        return id;
    }

    private Long getIdCheque() {
        Long id = (long) 1;
        if (!valoresAVista.getCheques().isEmpty()) {
            for (Cheque c : valoresAVista.getCheques()) {
                if (c.getId() >= id) {
                    id = c.getId() + 1;
                }
            }
        }
        return id;
    }

    private Long getCodigoItem() {
        Long id = (long) 1;
        if (!notaEmitida.getItensEmitidos().isEmpty()) {
            for (ItemEmitido dp : notaEmitida.getItensEmitidos()) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public BigDecimal getTotalCheque() {
        BigDecimal total = BigDecimal.ZERO;
        for (Cheque c : valoresAVista.getCheques()) {
            if (c.getCotacao() != null && c.getCotacao() != cotacao) {
                total = total.add(c.getValor().divide(c.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
            } else {
                total = total.add(c.getValor());
            }
        }
        return total;
    }

    public String getTotalChequeFormatado() {
        Locale local = configuracao.getMoedaPadrao().getBandeira().getLocal();
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);

        return nf.format(getTotalCheque());
    }

    //------------------- Fim Getter Personalizados ---------------------------
    //----------------------- Getters and Setters -----------------------------
    public ValoresAVistaBV getValoresAVista() {
        return valoresAVista;
    }

    public void setValoresAVista(ValoresAVistaBV valoresAVista) {
        this.valoresAVista = valoresAVista;
    }

    public NotaEmitida getNotaEmitidaSelecionada() {
        return notaEmitidaSelecionada;
    }

    public void setNotaEmitidaSelecionada(NotaEmitida notaEmitidaSelecionada) {
        this.notaEmitidaSelecionada = notaEmitidaSelecionada;
    }

    public NotaEmitidaBV getNotaEmitida() {
        return notaEmitida;
    }

    public List<TipoFormaDeRecebimentoParcela> getTiposDeFormaDeRecebimentoParcela() {
        return Arrays.asList(TipoFormaDeRecebimentoParcela.values());
    }

    public void setNotaEmitida(NotaEmitidaBV notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public ItemEmitidoBV getItemEmitido() {
        return itemEmitido;
    }

    public void setItemEmitido(ItemEmitidoBV itemEmitido) {
        this.itemEmitido = itemEmitido;
    }

    public ItemEmitido getItemEmitidoSelecionado() {
        return itemEmitidoSelecionado;
    }

    public void setItemEmitidoSelecionado(ItemEmitido itemEmitidoSelecionado) {
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

    public CotacaoValores getCotacaoValoresSelecionado() {
        return cotacaoValoresSelecionado;
    }

    public void setCotacaoValoresSelecionado(CotacaoValores cotacaoValoresSelecionado) {
        this.cotacaoValoresSelecionado = cotacaoValoresSelecionado;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public List<CotacaoValores> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<CotacaoValores> cotacoes) {
        this.cotacoes = cotacoes;
    }

    public List<ParcelaBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<ParcelaBV> parcelas) {
        this.parcelas = parcelas;
    }

    public NotaEmitida getNovoRegistroNE() {
        return novoRegistroNE;
    }

    public void setNovoRegistroNE(NotaEmitida novoRegistroNE) {
        this.novoRegistroNE = novoRegistroNE;
    }

    public ConfiguracaoService getConfiguracaoService() {
        return configuracaoService;
    }

    public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
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

    public ParcelaBV getParcelaSelecionada() {
        return parcelaSelecionada;
    }

    public void setParcelaSelecionada(ParcelaBV parcelaSelecionada) {
        this.parcelaSelecionada = parcelaSelecionada;
    }

    public ParcelaBV getParcelaBV() {
        return parcelaBV;
    }

    public void setParcelaBV(ParcelaBV parcelaBV) {
        this.parcelaBV = parcelaBV;
    }

    //------------------- Fim Getters and Setters -----------------------------
}
