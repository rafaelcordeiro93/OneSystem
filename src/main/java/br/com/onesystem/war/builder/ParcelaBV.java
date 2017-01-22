/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.BoletoDeCartaoBuilder;
import br.com.onesystem.domain.builder.ChequeBuilder;
import br.com.onesystem.domain.builder.TituloBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ParcelaBV implements Serializable {

    private Long id;
    private NotaEmitida notaEmitida;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private OperacaoFinanceira unidadeFinanceira;
    private Integer numeroParcela;
    private BigDecimal valor;
    private Date emissao;
    private Date vencimento;
    private Banco banco;
    private String agencia;
    private String conta;
    private String numeroCheque;
    private SituacaoDeCheque situacaoDeCheque;
    private BigDecimal multas;
    private BigDecimal juros;
    private BigDecimal descontos;
    private String emitente;
    private String observacao;
    private Cartao cartao;
    private String codigoTransacao;
    private SituacaoDeCartao tipoSituacaoCartao;
    private Moeda moeda;
    private Cambio cambio;
    private Recepcao recepcao;
    private TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela;

    public ParcelaBV() {
    }

    public ParcelaBV(Long id, NotaEmitida notaEmitida, ConhecimentoDeFrete conhecimentoDeFrete,
            OperacaoFinanceira unidadeFinanceira, Integer numeroParcela, BigDecimal valor,
            Date emissao, Date vencimento, Banco banco, String agencia, String conta,
            String numeroCheque, SituacaoDeCheque situacaoDeCheque, BigDecimal multas,
            BigDecimal juros, BigDecimal descontos, String emitente, String observacao,
            Cartao cartao, String codTransacao,
            SituacaoDeCartao tipoSituacaoCartao, Moeda moeda, Cambio cambio,
            Recepcao recepcao, TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela) {
        this.id = id;
        this.notaEmitida = notaEmitida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        this.unidadeFinanceira = unidadeFinanceira;
        this.numeroParcela = numeroParcela;
        this.valor = valor;
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCheque = numeroCheque;
        this.situacaoDeCheque = situacaoDeCheque;
        this.multas = multas;
        this.juros = juros;
        this.descontos = descontos;
        this.emitente = emitente;
        this.observacao = observacao;
        this.cartao = cartao;
        this.codigoTransacao = codTransacao;
        this.tipoSituacaoCartao = tipoSituacaoCartao;
        this.moeda = moeda;
        this.cambio = cambio;
        this.recepcao = recepcao;
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    public OperacaoFinanceira getUnidadeFinanceira() {
        return unidadeFinanceira;
    }

    public void setUnidadeFinanceira(OperacaoFinanceira unidadeFinanceira) {
        this.unidadeFinanceira = unidadeFinanceira;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(vencimento);
    }

    public String getDiaDaSemana() {
        DayOfWeek diaDaSemana = vencimento.toInstant().atZone(ZoneId.systemDefault()).getDayOfWeek();
        return diaDaSemana.getDisplayName(TextStyle.FULL, Locale.US);
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public SituacaoDeCheque getSituacaoDeCheque() {
        return situacaoDeCheque;
    }

    public void setSituacaoDeCheque(SituacaoDeCheque situacaoDeCheque) {
        this.situacaoDeCheque = situacaoDeCheque;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public void setMultas(BigDecimal multas) {
        this.multas = multas;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getDescontos() {
        return descontos;
    }

    public void setDescontos(BigDecimal descontos) {
        this.descontos = descontos;
    }

    public String getEmitente() {
        return emitente;
    }

    public void setEmitente(String emitente) {
        this.emitente = emitente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Integer getDias() {
        LocalDate venc = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long dias = LocalDate.now().until(venc, ChronoUnit.DAYS);
        return dias.intValue();
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public SituacaoDeCartao getTipoSituacaoCartao() {
        return tipoSituacaoCartao;
    }

    public void setTipoSituacaoCartao(SituacaoDeCartao tipoSituacaoCartao) {
        this.tipoSituacaoCartao = tipoSituacaoCartao;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public TipoFormaDeRecebimentoParcela getTipoFormaDeRecebimentoParcela() {
        return tipoFormaDeRecebimentoParcela;
    }

    public void setTipoFormaDeRecebimentoParcela(TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela) {
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
    }

    public BoletoDeCartao construirBoletoDeCartao(NotaEmitida notaEmitida) throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comCartao(cartao).comCodTransacao(codigoTransacao).
                comDias(getDias()).comEmissao(emissao).comNumeroParcela(numeroParcela).
                comTipoSituacao(SituacaoDeCartao.ABERTO).comValor(valor).comNotaEmitida(notaEmitida)
                .construir();
    }

    public Cheque construirCheque(NotaEmitida notaEmitida) throws DadoInvalidoException {
        return new ChequeBuilder().comAgencia(agencia).comBanco(banco).comConta(conta)
                .comEmissao(emissao).comEmitente(emitente).comNumeroCheque(numeroCheque)
                .comNumeroParcelas(numeroParcela).comObservacao(observacao).
                comTipoSituacao(SituacaoDeCheque.ABERTO).comValor(valor).comVencimento(vencimento)
                .comNotaEmitida(notaEmitida).construir();
    }

    public Titulo construirTitulo(NotaEmitida notaEmitida) throws DadoInvalidoException {
        return new TituloBuilder().comValor(valor).comSaldo(valor).comEmissao(emissao).comOperacaoFinanceira(OperacaoFinanceira.ENTRADA)
                .comTipoFormaPagRec(TipoFormaPagRec.A_PRAZO).comMoeda(moeda).comHistorico(observacao).
                comNotaEmitida(notaEmitida).construir();
    }

}
