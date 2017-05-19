/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.domain.builder.ParcelaBuilder;
import br.com.onesystem.exception.CurrencyMissmatchException;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.ValorPorCotacaoBV;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.DateUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.Money;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.builder.ChequeBV;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.builder.CreditoBV;
import br.com.onesystem.war.builder.NotaEmitidaBV;
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
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DesdobramentoDeVendaView extends BasicMBImpl<NotaEmitida, NotaEmitidaBV> implements Serializable {

    private Configuracao configuracao;
    private List<ValorPorCotacaoBV> cotacoes;
    private List<Cotacao> cotacaoLista;
    private Cotacao cotacao;
    private List<CobrancaBV> parcelas;
    private CobrancaBV cobranca;
    private CobrancaBV cobrancaSelecionada;
    private BoletoDeCartaoBV boletoDeCartao;
    private ChequeBV cheque;
    private Cheque chequeSelecionado;
    private CreditoBV creditoBV;

    @Inject
    private ConfiguracaoService configuracaoService;

    @Inject
    private CotacaoService service;

    @PostConstruct
    public void init() {

        iniciarConfiguracoes();
        inicializaCotacoes();
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

    private void inicializaCotacoes() {
        cotacaoLista = service.buscarCotacoesDoDiaAtual();
        cotacoes = new ArrayList<>();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new ValorPorCotacaoBV(c, null, null, null, configuracao.getMoedaPadrao()));
        }
    }

    @Override
    public void limparJanela() {
        e = new NotaEmitidaBV();
        e.setMoedaPadrao(configuracao.getMoedaPadrao());
        e.setCotacao(cotacao);
        cobranca = new CobrancaBV();
        cobrancaSelecionada = new CobrancaBV();
        parcelas = new ArrayList<>();
        cheque = new ChequeBV();
        chequeSelecionado = new Cheque();
        boletoDeCartao = new BoletoDeCartaoBV();
        creditoBV = new CreditoBV();
        limparChequeEntrada();
        inicializaCotacoes();
        // limparChequeParcelas();
    }

    @Override
    public void selecionar(SelectEvent event) {
        limparJanela();
        Object obj = event.getObject();
        if (obj instanceof NotaEmitida) {
            e = new NotaEmitidaBV((NotaEmitida) obj);
            for (Cobranca c : e.getCobrancas()) {
                if (c.getEntrada() == false) {
                    parcelas.add(new CobrancaBV(c));
                } else if (c.getEntrada() == true && e.getFormaDeRecebimento().isEntradaEmCartao()) {
                    // cartaoAVista = new Cartao(c);
                }
            }

        }

    }

    public BigDecimal getTotalParcelas() {
        BigDecimal totalParcela = BigDecimal.ZERO;

        for (Cobranca p : e.getCobrancas()) {
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

    public void selecionaChequeEntrada(SelectEvent event) {
        chequeSelecionado = (Cheque) event.getObject();
        cheque = new ChequeBV(chequeSelecionado);
    }

    public void geraBaixaDeCotacoes() throws DadoInvalidoException {
        for (ValorPorCotacaoBV c : cotacoes) {
            if (c.getValorAReceber().compareTo(BigDecimal.ZERO) > 0) {
                e.adiciona(c.construir());//
            }
        }
        // geraBoletoDeCartaoAVista();
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

    public void calculaValorAcrescimoEDesconto() {
        BigDecimal total = e.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pAcrescimo = e.getPorcentagemAcrescimo() == null ? BigDecimal.ZERO : e.getPorcentagemAcrescimo();
            BigDecimal pDesconto = e.getPorcentagemDesconto() == null ? BigDecimal.ZERO : e.getPorcentagemDesconto();
            BigDecimal acrescimo;
            BigDecimal desconto;
            BigDecimal cem = new BigDecimal(100);

            if (pAcrescimo.compareTo(BigDecimal.ZERO) > 0) {
                acrescimo = (pAcrescimo.multiply(total)).divide(cem, 2, BigDecimal.ROUND_UP);
                e.setAcrescimo(acrescimo);
            } else {
                e.setAcrescimo(BigDecimal.ZERO);
            }
            if (pDesconto.compareTo(BigDecimal.ZERO) > 0) {
                desconto = (pDesconto.multiply((total))).divide(cem, 2, BigDecimal.ROUND_UP);
                e.setDesconto(desconto);
            } else {
                e.setDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(e.getTotalNota());
        }
    }

    public void calculaPorcentagemAcrescimoEDesconto() {
        BigDecimal total = e.getTotalItens();
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal acrescimo = e.getAcrescimo() == null ? BigDecimal.ZERO : e.getAcrescimo();
            BigDecimal desconto = e.getDesconto() == null ? BigDecimal.ZERO : e.getDesconto();
            BigDecimal pAcrescimo;
            BigDecimal pDesconto;
            BigDecimal cem = new BigDecimal(100);

            if (acrescimo.compareTo(BigDecimal.ZERO) > 0) {
                pAcrescimo = (acrescimo.multiply(cem)).divide(total, 2, BigDecimal.ROUND_UP);
                e.setPorcentagemAcrescimo(pAcrescimo);
            } else {
                e.setPorcentagemAcrescimo(BigDecimal.ZERO);
            }
            if (desconto.compareTo(BigDecimal.ZERO) > 0) {
                pDesconto = (desconto.multiply(cem)).divide(((total)), 2, BigDecimal.ROUND_UP);
                e.setPorcentagemDesconto(pDesconto);
            } else {
                e.setPorcentagemDesconto(BigDecimal.ZERO);
            }

            incluiValorDeFormaDeRecebimento(e.getTotalNota());
        }
    }

    private void incluiValorDeFormaDeRecebimento(BigDecimal resultado) {
        switch (e.getFormaDeRecebimento().getFormaPadraoDeEntrada()) {
            case DINHEIRO:
                e.setTotalEmDinheiro(resultado);
                break;
            case CREDITO:
                // e.setCredito(resultado);
                break;
            case CHEQUE:
//                valoresAVista.setCheque(resultado);
                break;
            case A_FATURAR:
                e.setAFaturar(resultado);
                break;
            case CARTAO:
//                valoresAVista.setCartao(resultado);
                break;
            default:
                break;
        }
    }

    public List<TipoFormaDeRecebimentoParcela> getTiposDeFormaDeRecebimentoParcela() {
        List<TipoFormaDeRecebimentoParcela> forma = new ArrayList<>();
        if (e.getFormaDeRecebimento().isParcelaEmCartao()) {
            forma.add(TipoFormaDeRecebimentoParcela.CARTAO);
        }
        if (e.getFormaDeRecebimento().isParcelaEmCheque()) {
            forma.add(TipoFormaDeRecebimentoParcela.CHEQUE);
        }
        if (e.getFormaDeRecebimento().isParcelaEmConta()) {
            forma.add(TipoFormaDeRecebimentoParcela.TITULO);
        }
        return forma;
    }

    public void detalharParcela() {
        cobranca = new CobrancaBV(cobrancaSelecionada);
        RequestContext req = RequestContext.getCurrentInstance();
        if (cobrancaSelecionada.getTipoFormaDeRecebimentoParcela() == TipoFormaDeRecebimentoParcela.CHEQUE) {
            req.execute("PF('detalheChequeParcela').show()");
            req.update("conteudo:panelDetCheParcela");
        } else if (cobrancaSelecionada.getTipoFormaDeRecebimentoParcela() == TipoFormaDeRecebimentoParcela.CARTAO) {
            req.execute("PF('detalheCartaoParcela').show()");
            req.update("conteudo:panelDetCartaoPar");
        }
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

        BigDecimal chequeTotal = e.getTotalChequeDeEntrada() == null ? BigDecimal.ZERO : e.getTotalChequeDeEntrada();
        BigDecimal cartao = boletoDeCartao.getValor() == null ? BigDecimal.ZERO : boletoDeCartao.getValor();
        BigDecimal credito = creditoBV.getValor() == null ? BigDecimal.ZERO : creditoBV.getValor();
        BigDecimal dinheiro = e.getTotalEmDinheiro() == null ? BigDecimal.ZERO : e.getTotalEmDinheiro();
        BigDecimal totalParcelas = getTotalParcelas() == null ? BigDecimal.ZERO : getTotalParcelas();
        BigDecimal soma = chequeTotal.add(cartao).add(credito).add(totalParcelas).add(dinheiro);

        e.setAFaturar(e.getTotalNota().subtract(soma));
    }

    public void criaParcelas() {
        try {
            if (e.getAFaturar() != null && (e.getAFaturar().compareTo(BigDecimal.ZERO) > 0
                    || getTotalParcelas().compareTo(BigDecimal.ZERO) > 0)) {
                Integer numParcelas = e.getNumeroParcelas(); //Número de cobranca
                TipoPeriodicidade tipoPeridiocidade = e.getFormaDeRecebimento().getTipoPeriodicidade();
                Integer periodicidade = e.getFormaDeRecebimento().getPeriodicidade();

                if (numParcelas != null && numParcelas > 0) {

                    BigDecimal soma = e.getAFaturar().add(getTotalParcelas());
                    Money m = Money.valueOf(soma.toString(), "USD");
                    Money[] distribute = m.distribute(numParcelas);

                    // Busca o primeiro vencimento das cobranca
                    Date vencimento = new DateUtil().getPeriodicidadeCalculada(new Date(), tipoPeridiocidade, periodicidade);

                    parcelas = new ArrayList<>();
                    for (int i = 0; i < numParcelas; i++) {
                        parcelas.add(new ParcelaBuilder().comID(getIdParcela()).comValor(distribute[i].getAmount())
                                .comVencimento(vencimento).comDias(getDiasDeVencimento(vencimento)).comCotacao(cotacao).comEmissao(e.getEmissao())
                                .comTipoFormaDeRecebimentoParcela(e.getFormaDeRecebimento().getFormaPadraoDeParcela()).comCodigoTransacao("000000")
                                .comOperacaoFinanceira(e.getOperacao().getOperacaoFinanceira()).comCartao(e.getFormaDeRecebimento().getCartao())
                                .comSituacaoDeCartao(SituacaoDeCartao.ABERTO).comSituacaoDeCheque(SituacaoDeCheque.ABERTO).comPessoa(e.getPessoa())
                                .comEntrada(false).comTipoLancamento(TipoLancamento.EMITIDA).construir());
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

    public Integer getDiasDeVencimento(Date vencimento) {
        LocalDate venc = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long dias = LocalDate.now().until(venc, ChronoUnit.DAYS);
        return dias.intValue();
    }

    private Long getIdParcela() {
        Long id = (long) 1;
        if (!parcelas.isEmpty()) {
            for (CobrancaBV p : parcelas) {
                if (p.getId() >= id) {
                    id = p.getId() + 1;
                }
            }
        }
        return id;
    }

    /**
     * Adiciona o cheque dentro da lista de cheques na janela
     * detalheChequeEntrada.
     */
    public void addChequeEntrada() {
        try {
            //Prepara e constroi cheque
            cheque.setEntrada(true);
            cheque.setTipoSituacao(SituacaoDeCheque.ABERTO);
            cheque.setOperacaoFinanceira(e.getOperacao().getOperacaoFinanceira());
            Cheque c = cheque.construirComID();

            e.adiciona(c); //Adiciona cheque a lista
            limparChequeEntrada(); //Limpa cheque
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateChequeEntrada() {
        try {
            if (chequeSelecionado != null) {
                Cheque c = cheque.construirComID(); //Constroi cheque
                e.atualiza(chequeSelecionado, c); //Atualiza cheque na lsita
                limparChequeEntrada(); //Limpa cheque
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteChequeEntrada() {
        if (chequeSelecionado != null) {
            e.remove(chequeSelecionado); //Remove cheque
        }
        limparChequeEntrada();
    }

    public void limparChequeEntrada() {
        chequeSelecionado = null;
        cheque = new ChequeBV();
        cheque.setCotacao(cotacao);
    }

    public void addDetalheParcelaCheque() {
        try {

            //Seleciona o cheque na parcela
            cobrancaSelecionada.setBanco(cobranca.getBanco());
            cobrancaSelecionada.setAgencia(cobranca.getAgencia());
            cobrancaSelecionada.setNumeroCheque(cobranca.getNumeroCheque());
            cobrancaSelecionada.setConta(cobranca.getConta());
            cobrancaSelecionada.setEmitente(cobranca.getEmitente());
            cobrancaSelecionada.setObservacao(cobranca.getObservacao());
            parcelas.set(parcelas.indexOf(cobrancaSelecionada), cobrancaSelecionada);
            cobranca.construirCheque(); // Constroi Cheque para validar.

            cobranca = new CobrancaBV();
            cobrancaSelecionada = null;

            RequestContext r = RequestContext.getCurrentInstance();
            r.update("conteudo:ne:neParcelas");
            r.execute("PF('detalheChequeParcela').hide()");

        } catch (DadoInvalidoException ex) {
            ex.print();
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
            boletoDeCartao.setEmissao(e.getEmissao());
            boletoDeCartao.setPessoa(e.getPessoa());
            boletoDeCartao.setOperacaoFinanceira(e.getOperacao().getOperacaoFinanceira());
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

    public void addDetalheParcelaCartao() {
        try {
            cobranca.setSituacaoDeCartao(SituacaoDeCartao.ABERTO);
            cobranca.construirBoletoDeCartao(); // Constroi Boleto de Cartão para validar.

            //Seleciona o cartao na parcela
            cobrancaSelecionada.setCartao(cobranca.getCartao());
            cobrancaSelecionada.setCodigoTransacao(cobranca.getCodigoTransacao());
            parcelas.set(parcelas.indexOf(cobrancaSelecionada), cobrancaSelecionada);
            cobranca = new CobrancaBV();
            cobrancaSelecionada = null;
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getConfiguracaoService() {
        return configuracaoService;
    }

    public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }

    public List<ValorPorCotacaoBV> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<ValorPorCotacaoBV> cotacoes) {
        this.cotacoes = cotacoes;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }

    public List<CobrancaBV> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<CobrancaBV> parcelas) {
        this.parcelas = parcelas;
    }

    public CobrancaBV getCobranca() {
        return cobranca;
    }

    public void setCobranca(CobrancaBV cobranca) {
        this.cobranca = cobranca;
    }

    public CobrancaBV getCobrancaSelecionada() {
        return cobrancaSelecionada;
    }

    public void setCobrancaSelecionada(CobrancaBV cobrancaSelecionada) {
        this.cobrancaSelecionada = cobrancaSelecionada;
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

    public Cheque getChequeSelecionado() {
        return chequeSelecionado;
    }

    public void setChequeSelecionado(Cheque chequeSelecionado) {
        this.chequeSelecionado = chequeSelecionado;
    }

}
