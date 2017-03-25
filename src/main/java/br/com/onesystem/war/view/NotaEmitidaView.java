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
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.domain.builder.ParcelaBuilder;
import br.com.onesystem.exception.CurrencyMissmatchException;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.CotacaoValores;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.DateUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.Money;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.builder.CreditoBV;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@ManagedBean
@ViewScoped
public class NotaEmitidaView extends BasicMBImpl<NotaEmitida> implements Serializable {

    private ValoresAVistaBV valoresAVista;
    private CreditoBV creditoBV;
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
    private List<ItemEmitido> itensEmitidos;
    private ParcelaBV parcelaSelecionada;
    private ParcelaBV parcelaBV;
    private BoletoDeCartaoBV boletoDeCartao;
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
        notaEmitida = new NotaEmitidaBV();
        creditoBV = new CreditoBV();
        itemEmitido = new ItemEmitidoBV();
        itensEmitidos = new ArrayList<>();
        valoresAVista = new ValoresAVistaBV(cotacao);
        boletoDeCartao = new BoletoDeCartaoBV();
        parcelas = new ArrayList<>();
        notaEmitidaSelecionada = null;
        cheque = new ChequeBV();
        inicializaCotacoes();
        limparChequeEntrada();
        parcelaBV = new ParcelaBV();
        limpaSessao();
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
        cotacoes = new ArrayList<>();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new CotacaoValores(c, null, null, null, configuracao.getMoedaPadrao()));
        }
    }

    // --------------------- Fim Inicializa Janela ----------------------------
    // -------------- Operações para criação da entidade ----------------------   
    public void geraItensEmitidos() {
        try {
            if (!itensEmitidos.isEmpty()) {
                notaEmitida.setItensEmitidos(new ArrayList<ItemEmitido>());
                notaEmitida.setEmissao(new Date());
                for (ItemEmitido ie : itensEmitidos) {
                    ItemEmitidoBV ieBV = new ItemEmitidoBV(ie);
                    notaEmitida.getItensEmitidos().add(ieBV.construir());
                }
                validaAFaturar();
            } else {
                ErrorMessage.print(new BundleUtil().getMessage("Itens_Devem_Ser_Informados"));
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void validaAFaturar() {
        // Se valor a faturar maior que zero deve exibir diálogo de confirmação
        if (valoresAVista.getAFaturar() != null && valoresAVista.getAFaturar().compareTo(BigDecimal.ZERO) > 0) {
            RequestContext c = RequestContext.getCurrentInstance();
            c.execute("PF('existeValorAFaturar').show()");
        } else if (valoresAVista.getAFaturar() != null && valoresAVista.getAFaturar().compareTo(BigDecimal.ZERO) < 0) {
            ErrorMessage.print(new BundleUtil().getMessage("Valor_A_Faturar_Menor_Que_Zero"));
        } else {
            validaDinheiro();
        }
    }

    public void validaDinheiro() {
        // Se existir valor em dinheiro abre a janela de cotações.
        if (valoresAVista.getDinheiro() != null && valoresAVista.getDinheiro().compareTo(BigDecimal.ZERO) > 0) {
            recalculaCotacoes(); // abre a janela de cotações
        } else {
            notaEmitida.setBaixas(null);
            geraValoresAVista();
        }
    }

    /**
     * Deve gerar todos os valores a vista e incluir na nota emitida
     */
    public void geraValoresAVista() {
        try {
            //Constroi boleto de Cartão
            if (boletoDeCartao.getValor() != null && boletoDeCartao.getValor().compareTo(BigDecimal.ZERO) > 0) {
                valoresAVista.setBoletoDeCartao(boletoDeCartao.construir());
            }

            //Constroi 
            ValoresAVista v = null;

            //Constroi cheques e ValoresAVista             
            List<Cheque> chequesRecebidos = new ArrayList<>();
            if (valoresAVista.getCheques() != null) {
                if (!valoresAVista.getCheques().isEmpty()) {
                    v = valoresAVista.construir();
                    for (Cheque c : valoresAVista.getCheques()) {
                        ChequeBV bv = new ChequeBV(c);
                        bv.setValoresAVista(v);
                        chequesRecebidos.add(bv.construir());
                    }
                    //Add cheque a ValoresAVista
                    v.setCheques(chequesRecebidos);
                } else {
                    valoresAVista.setCheques(null);
                    v = valoresAVista.construir();
                }
            } else {
                v = valoresAVista.construir();
            }

            //Add ValoresAVista a nota emitida
            notaEmitida.setValoresAVista(v);
            geraParcelas();
        } catch (DadoInvalidoException die) {
            die.printConsole();
            die.print();
        }

    }

    /**
     * Gera as parcelas para persistência.
     *
     * @throws br.com.onesystem.exception.DadoInvalidoException
     */
    public void geraParcelas() {
        try {
            List<BoletoDeCartao> cartoes = new ArrayList<>();
            List<Cheque> cheques = new ArrayList<>();
            List<Titulo> titulos = new ArrayList<>();

            //Gera as parcelas de acordo a sua modalidade.
            for (ParcelaBV p : parcelas) {
                p.setEmissao(notaEmitida.getEmissao());
                switch (p.getTipoFormaDeRecebimentoParcela()) {
                    case CARTAO:
                        cartoes.add(p.construirBoletoDeCartao());
                        break;
                    case CHEQUE:
                        cheques.add(p.construirCheque());
                        break;
                    case TITULO:
                        titulos.add(p.construirTitulo());
                        break;
                    default:
                        break;
                }
            }

            // Inclui as parcelas dentro da nota emitida.
            notaEmitida.setCartoes(cartoes.isEmpty() ? null : cartoes);
            notaEmitida.setCheques(cheques.isEmpty() ? null : cheques);
            notaEmitida.setTitulos(titulos.isEmpty() ? null : titulos);

            finalizar();
        } catch (DadoInvalidoException die) {
            ErrorMessage.print(new BundleUtil().getMessage("Erro_ao_gerar_parcelas"));
            die.print();
        }
    }

    /**
     * Gera a entidade com os dados de recebimento, parcelas, itens e cotações.
     */
    public void finalizar() {
        try {

            //Constroi a nota
            NotaEmitida ne = notaEmitida.construirComID();

            //Inclui a nota nos itens
            preparaInclusaoDeItemEmitido(ne);

            //Inclui a nota nas cotacoes
            preparaInclusaoDeCotacoes(ne);

            //Inclui a nota nos valores a vista
            preparaInclusaoDeValoresAVista(ne);

            //Inclui a nota nas parcelas
            preparaInclusaoDeParcelas(ne);

            add(ne);

        } catch (DadoInvalidoException die) {
            die.printConsole();
            die.print();
        } catch (ConstraintViolationException cpe) {
            ErrorMessage.printConsole(cpe.toString());
            ErrorMessage.print(cpe.toString());
        }
    }

    /**
     * Prepara valor de recebimento e insere a entidade no banco de dados
     *
     * @param ne Nota Emitida pronta para ser persistida
     * @throws br.com.onesystem.exception.DadoInvalidoException
     */
    public void add(NotaEmitida ne) throws ConstraintViolationException, DadoInvalidoException {
        new AdicionaDAO<NotaEmitida>().adiciona(ne);
        InfoMessage.adicionado();
        System.out.println(ne);
        limparJanela();
    }

    /**
     * Inclui a nota nas parcelas
     */
    private void preparaInclusaoDeParcelas(NotaEmitida ne) throws EDadoInvalidoException {

        //Inclui a nota nos Cartoes
        if (ne.getCartoes() != null) {
            for (BoletoDeCartao b : ne.getCartoes()) {
                if (b.getCartao() == null) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("cartao_not_null"));
                }
                b.setNotaEmitida(ne);
            }
        }

        //Inclui a nota nos Cheques
        if (ne.getCheques() != null) {
            for (Cheque c : ne.getCheques()) {
                c.setNotaEmitida(ne);
            }
        }

        //Inclui a nota nos Titulos
        if (ne.getTitulos() != null) {
            for (Titulo t : ne.getTitulos()) {
                t.setNotaEmitida(ne);
            }
        }
    }

    /**
     * Inclui a nota nos valores a vista
     */
    private void preparaInclusaoDeValoresAVista(NotaEmitida ne) {
        //Inclui a nota no valor a vista
        if (ne.getValoresAVista() != null) {
            ne.getValoresAVista().setNotaEmitida(ne);

            //Inclui a nota no cheque a vista
            if (ne.getValoresAVista().getCheques() != null) {
                for (Cheque c : ne.getValoresAVista().getCheques()) {
                    c.setNotaEmitida(ne);
                }
            }

            //Inclui a nota no boleto de cartao a vista
            if (ne.getValoresAVista().getBoletoCartao() != null) {
                ne.getValoresAVista().getBoletoCartao().setNotaEmitida(ne);
            }
        }
    }

    /**
     * Inclui a nota nas cotações
     */
    private void preparaInclusaoDeCotacoes(NotaEmitida ne) {
        if (ne.getBaixaDinheiro() != null) {
            for (Baixa b : ne.getBaixaDinheiro()) {
                b.setNotaEmitida(ne);
            }
        }
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
    private void preparaInclusaoDeItemEmitido(NotaEmitida ne) throws ConstraintViolationException, DadoInvalidoException {
        for (ItemEmitido ie : ne.getItensEmitidos()) {
            ie.preparaInclusao(ne); // Informa a Nota Emitida.
            for (Estoque estoque : ie.getEstoques()) {
                estoque.preparaInclusaoDe(ie, notaEmitida.getEmissao()); // Exclui ID do Estoque e informa o Item Emitido.
            }
        }
    }

    private List<Estoque> criarBaixaDeEstoque(List<QuantidadeDeItemBV> lista) throws DadoInvalidoException {
        List<Estoque> estoquesBV = new ArrayList<>();
        try {
            for (QuantidadeDeItemBV q : lista) {
                for (OperacaoDeEstoque operacaoDeEstoque : notaEmitida.getOperacao().getOperacaoDeEstoque()) {
                    estoqueBV = new EstoqueBV(q.getSaldoDeEstoque().getDeposito(), q.getQuantidade(),
                            itemEmitido.getItem(), operacaoDeEstoque, null, notaEmitida.getEmissao(), null);
                    // Adiciona no estoque
                    estoquesBV.add(estoqueBV.construir());
                }
            }
        } catch (NullPointerException npe) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("operacao_not_null"));
        }
        return estoquesBV;
    }

    /**
     * Calcula a cotacao inicial ao abrir a janela de cotacoes
     */
    public void calculaCotacaoInicial() {
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("cotacaoVal");
        if (valoresAVista.getDinheiro().compareTo(getTotalConvertidoRecebido()) > 0) {
            for (CotacaoValores c : cotacoes) {
                if (c.getCotacao().getConta().getMoeda().equals(configuracao.getMoedaPadrao())) {
                    c.setValorAReceber(valoresAVista.getDinheiro());
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
        for (CotacaoValores c : cotacoes) {
            c.setTotal(valoresAVista.getDinheiro());
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
        List<Baixa> baixas = new ArrayList<>();
        for (CotacaoValores c : cotacoes) {
            if (c.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
                Baixa baixa = new BaixaBuilder().cancelada(false)
                        .comCotacao(c.getCotacao()).comEmissao(notaEmitida.getEmissao())
                        .comNaturezaFinanceira(notaEmitida.getOperacao().getOperacaoFinanceira())
                        .comPessoa(notaEmitida.getPessoa()).comReceita(notaEmitida.getOperacao().getVendaAVista())
                        .comValor(c.getValorAReceber()).construir();
                baixas.add(baixa);
            }
        }
        notaEmitida.setBaixas(baixas);

        geraValoresAVista();
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
                //valoresAVista.setCredito(resultado);
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
            ItemEmitido ie = itemEmitido.construirComId();

            itensEmitidos.add(ie);
            limparItemEmitido();

            recalculaValores();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateItemNaLista() {
        try {
            if (itemEmitidoSelecionado != null) {
                itensEmitidos.set(itensEmitidos.indexOf(itemEmitidoSelecionado),
                        itemEmitido.construirComId());
                limparItemEmitido();
                recalculaValores();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteItemNaLista() {
        if (itemEmitidoSelecionado != null) {
            itensEmitidos.remove(itemEmitidoSelecionado);
            limparItemEmitido();
            recalculaValores();
        }
    }

    public void limparItemEmitido() {
        itemEmitido = new ItemEmitidoBV();
        itemEmitidoSelecionado = null;
    }

    public String getTotalItensFormatado() {
        if (itensEmitidos.isEmpty()) {
            return "";
        } else {
            return configuracao.getMoedaPadrao().getSigla() + " " + NumberFormat.getNumberInstance().format(getTotalItens());
        }
    }

    private BigDecimal getTotalItens() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemEmitido i : itensEmitidos) {
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
                                .comTipoFormaDeRecebimentoParcela(notaEmitida.getFormaDeRecebimento().getFormaPadraoDeParcela()).comCodigoTransacao("000000")
                                .comOperacaoFinanceira(notaEmitida.getOperacao().getOperacaoFinanceira()).comCartao(notaEmitida.getFormaDeRecebimento().getCartao())
                                .comSituacaoDeCartao(SituacaoDeCartao.ABERTO).comSituacaoDeCheque(SituacaoDeCheque.ABERTO)
                                .comTipoLancamento(TipoLancamento.EMITIDA).construir());
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
        }
    }

    /**
     * Adiciona o cheque dentro da lista de cheques na janela
     * detalheChequeEntrada.
     */
    public void addChequeEntrada() {
        try {
            //Prepara e constroi cheque
            cheque.setId(getIdCheque());
            cheque.setTipoSituacao(SituacaoDeCheque.ABERTO);
            cheque.setTipoLancamento(TipoLancamento.EMITIDA);
            Cheque c = cheque.construirComID();

            //Adiciona cheque a lista
            if (valoresAVista.getCheques() == null) {
                valoresAVista.setCheques(new ArrayList<Cheque>());
            }
            valoresAVista.getCheques().add(c);

            //Limpa cheque
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

    public void addCartaoQuandoSelecionadoNaParcela(ParcelaBV parcela) {
        if (parcela.getTipoFormaDeRecebimentoParcela() == TipoFormaDeRecebimentoParcela.CARTAO) {
            for (ParcelaBV p : parcelas) {
                if (p.getId().equals(parcela.getId())) {
                    p.setCartao(notaEmitida.getFormaDeRecebimento().getCartao());
                    p.setCodigoTransacao("000000");
                }
            }
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

    public void limparChequeParcelas() {
        parcelaBV.setBanco(null);
        parcelaBV.setAgencia(null);
        parcelaBV.setNumeroCheque(null);
        parcelaBV.setConta(null);
        parcelaBV.setEmitente(null);
        parcelaBV.setObservacao(null);
    }

    // ------------------------- Fim Parcelas ---------------------------------
    // ----------------------------- Selecao ----------------------------------
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();

        if (obj instanceof Operacao) {
            Operacao operacao = (Operacao) obj;
            if (((Operacao) obj).getOperacaoDeEstoque().isEmpty()) {
                RequestContext rc = RequestContext.getCurrentInstance();
                rc.execute("PF('notaOperacaoNaoRelacionadaDialog').show()");
            } else {
                notaEmitida.setOperacao((Operacao) obj);
            }
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
            recalculaValores();
        } else if (obj instanceof Banco) {
            cheque.setBanco((Banco) obj);
        } else if (obj instanceof Cartao) {
            boletoDeCartao.setCartao((Cartao) obj);
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
            parcelaBV.construirBoletoDeCartao(); // Constroi Boleto de Cartão para validar.

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

            //Seleciona o cheque na parcela
            parcelaSelecionada.setBanco(parcelaBV.getBanco());
            parcelaSelecionada.setAgencia(parcelaBV.getAgencia());
            parcelaSelecionada.setNumeroCheque(parcelaBV.getNumeroCheque());
            parcelaSelecionada.setConta(parcelaBV.getConta());
            parcelaSelecionada.setEmitente(parcelaBV.getEmitente());
            parcelaSelecionada.setObservacao(parcelaBV.getObservacao());
            parcelas.set(parcelas.indexOf(parcelaSelecionada), parcelaSelecionada);
            parcelaBV.construirCheque(); // Constroi Cheque para validar.

            parcelaBV = new ParcelaBV();
            parcelaSelecionada = null;

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
        BigDecimal total = getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pAcrescimo = valoresAVista.getPorcentagemAcrescimo() == null ? BigDecimal.ZERO : valoresAVista.getPorcentagemAcrescimo();
            BigDecimal pDesconto = valoresAVista.getPorcentagemDesconto() == null ? BigDecimal.ZERO : valoresAVista.getPorcentagemDesconto();
            BigDecimal acrescimo;
            BigDecimal desconto;
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
            BigDecimal pAcrescimo;
            BigDecimal pDesconto;
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

    public void deleteBoletoDeCartao() {
        boletoDeCartao = new BoletoDeCartaoBV();
    }

    public void constroiBoletoDeCartaoEntrada() {
        RequestContext rc = RequestContext.getCurrentInstance();
        try {
            boletoDeCartao.setCotacao(cotacao);
            boletoDeCartao.setSituacao(SituacaoDeCartao.ABERTO);
            boletoDeCartao.setEmissao(notaEmitida.getEmissao());

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

        BigDecimal chequeTotal = getTotalCheque() == null ? BigDecimal.ZERO : getTotalCheque();
        BigDecimal cartao = boletoDeCartao.getValor() == null ? BigDecimal.ZERO : boletoDeCartao.getValor();
        BigDecimal credito = creditoBV.getValor() == null ? BigDecimal.ZERO : creditoBV.getValor();
        BigDecimal dinheiro = valoresAVista.getDinheiro() == null ? BigDecimal.ZERO : valoresAVista.getDinheiro();
        BigDecimal totalParcelas = getTotalParcelas() == null ? BigDecimal.ZERO : getTotalParcelas();
        BigDecimal soma = chequeTotal.add(cartao).add(credito).add(totalParcelas).add(dinheiro);

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

    public BigDecimal getTotalConvertidoRecebido() {
        BigDecimal total = BigDecimal.ZERO;
        for (CotacaoValores c : cotacoes) {
            total = total.add(c.getValorConvertidoRecebido());
        }
        return total;
    }

    public String getTotalConvertidoRecebidoFormatado() {
        return NumberFormat.getCurrencyInstance(configuracao.getMoedaPadrao().getBandeira().getLocal()).format(getTotalConvertidoRecebido());
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
        try {
            if (!valoresAVista.getCheques().isEmpty()) {
                for (Cheque c : valoresAVista.getCheques()) {
                    if (c.getId() >= id) {
                        id = c.getId() + 1;
                    }
                }
            }
        } catch (NullPointerException npe) {
            return id;
        }
        return id;
    }

    private Long getCodigoItem() {
        Long id = (long) 1;
        if (!itensEmitidos.isEmpty()) {
            for (ItemEmitido dp : itensEmitidos) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public BigDecimal getTotalCheque() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            for (Cheque c : valoresAVista.getCheques()) {
                if (c.getCotacao() != null && c.getCotacao() != cotacao) {
                    total = total.add(c.getValor().divide(c.getCotacao().getValor(), 2, BigDecimal.ROUND_UP));
                } else {
                    total = total.add(c.getValor());
                }
            }
        } catch (NullPointerException npe) {
            return null;
        }
        return total;
    }

    public String getTotalChequeFormatado() {
        Locale local = configuracao.getMoedaPadrao().getBandeira().getLocal();
        NumberFormat nf = NumberFormat.getCurrencyInstance(local);

        return getTotalCheque() == null ? null : nf.format(getTotalCheque());
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
        List<TipoFormaDeRecebimentoParcela> forma = new ArrayList<>();
        if (notaEmitida.getFormaDeRecebimento().isParcelaEmCartao()) {
            forma.add(TipoFormaDeRecebimentoParcela.CARTAO);
        }
        if (notaEmitida.getFormaDeRecebimento().isParcelaEmCheque()) {
            forma.add(TipoFormaDeRecebimentoParcela.CHEQUE);
        }
        if (notaEmitida.getFormaDeRecebimento().isParcelaEmConta()) {
            forma.add(TipoFormaDeRecebimentoParcela.TITULO);
        }
        return forma;
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

    public List<ItemEmitido> getItensEmitidos() {
        return itensEmitidos;
    }

    public void setItensEmitidos(List<ItemEmitido> itensEmitidos) {
        this.itensEmitidos = itensEmitidos;
    }

    //------------------- Fim Getters and Setters -----------------------------
}
