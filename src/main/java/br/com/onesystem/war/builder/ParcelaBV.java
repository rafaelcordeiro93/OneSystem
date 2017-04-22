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
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
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
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
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
    private SituacaoDeCartao situacaoDeCartao;
    private Moeda moeda;
    private Cambio cambio;
    private Recepcao recepcao;
    private TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela;
    private Integer dias;
    private Cotacao cotacao;
    private TipoLancamento tipoLancamento;
    private Pessoa pessoa;

    public ParcelaBV() {
    }

    public ParcelaBV(ParcelaBV p) {
        this.id = p.getId();
        this.notaEmitida = p.getNotaEmitida();
        this.conhecimentoDeFrete = p.getConhecimentoDeFrete();
        this.unidadeFinanceira = p.getUnidadeFinanceira();
        this.valor = p.getValor();
        this.emissao = p.getEmissao();
        this.vencimento = p.getVencimento();
        this.banco = p.getBanco();
        this.agencia = p.getAgencia();
        this.conta = p.getConta();
        this.numeroCheque = p.getNumeroCheque();
        this.situacaoDeCheque = p.getSituacaoDeCheque();
        this.multas = p.getMultas();
        this.juros = p.getJuros();
        this.descontos = p.getDescontos();
        this.emitente = p.getEmitente();
        this.observacao = p.getObservacao();
        this.cartao = p.getCartao();
        this.codigoTransacao = p.getCodigoTransacao();
        this.situacaoDeCartao = p.getSituacaoDeCartao();
        this.moeda = p.getMoeda();
        this.cambio = p.getCambio();
        this.recepcao = p.getRecepcao();
        this.tipoFormaDeRecebimentoParcela = p.getTipoFormaDeRecebimentoParcela();
        this.dias = p.getDias();
        this.cotacao = p.getCotacao();
        this.tipoLancamento = p.getTipoLancamento();
        this.pessoa = p.getPessoa();
    }

    public ParcelaBV(Long id, NotaEmitida notaEmitida, ConhecimentoDeFrete conhecimentoDeFrete,
            OperacaoFinanceira unidadeFinanceira, BigDecimal valor,
            Date emissao, Date vencimento, Banco banco, String agencia, String conta,
            String numeroCheque, SituacaoDeCheque situacaoDeCheque, BigDecimal multas,
            BigDecimal juros, BigDecimal descontos, String emitente, String observacao,
            Cartao cartao, String codigoTransacao,
            SituacaoDeCartao tipoSituacaoCartao, Moeda moeda, Cambio cambio,
            Recepcao recepcao, TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela,
            Integer dias, Cotacao cotacao, TipoLancamento tipoLancamento, Pessoa pessoa) {
        this.id = id;
        this.notaEmitida = notaEmitida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        this.unidadeFinanceira = unidadeFinanceira;
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
        this.codigoTransacao = codigoTransacao;
        this.situacaoDeCartao = tipoSituacaoCartao;
        this.moeda = moeda;
        this.cambio = cambio;
        this.recepcao = recepcao;
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
        this.dias = dias;
        this.tipoLancamento = tipoLancamento;
        this.cotacao = cotacao;
        this.pessoa = pessoa;
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

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public OperacaoFinanceira getUnidadeFinanceira() {
        return unidadeFinanceira;
    }

    public void setUnidadeFinanceira(OperacaoFinanceira unidadeFinanceira) {
        this.unidadeFinanceira = unidadeFinanceira;
    }

    public String getValorFormatado() {
        return NumberFormat.getCurrencyInstance(cotacao.getConta().getMoeda().getBandeira().getLocal()).format(valor);
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
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
        return dias;
    }

    public void calcularVencimento() {
        LocalDate venc = LocalDate.now();
        venc = venc.plusDays(new Long(dias));
        vencimento = Date.from(venc.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public void calcularDiasDeVencimento() {
        LocalDate venc = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long dias = LocalDate.now().until(venc, ChronoUnit.DAYS);
        setDias(dias.intValue());
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public SituacaoDeCartao getSituacaoDeCartao() {
        return situacaoDeCartao;
    }

    public void setSituacaoDeCartao(SituacaoDeCartao situacaoDeCartao) {
        this.situacaoDeCartao = situacaoDeCartao;
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

    public String getDescricaoCheque() {
        return banco == null ? "" : banco.getNome() + " / " + agencia + " / " + conta + " Nº " + numeroCheque;
    }

    public String getDescricaoCartao() {
        return cartao == null ? "" : cartao.getNome() + " Nº " + codigoTransacao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public TipoFormaDeRecebimentoParcela getTipoFormaDeRecebimentoParcela() {
        return tipoFormaDeRecebimentoParcela;
    }

    public void setTipoFormaDeRecebimentoParcela(TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela) {
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
    }

    public BoletoDeCartao construirBoletoDeCartao() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comCartao(cartao).comCodigoTransacao(codigoTransacao).
                comVencimento(vencimento).comEmissao(emissao).comCotacao(cotacao).comPessoa(pessoa)
                .comTipoSituacao(SituacaoDeCartao.ABERTO).comValor(valor).comOperacaoFinanceira(unidadeFinanceira)
                .construir();
    }

    public Cheque construirCheque() throws DadoInvalidoException {
        return new ChequeBuilder().comAgencia(agencia).comBanco(banco).comConta(conta).comOperacaoFinanceira(unidadeFinanceira).comPessoa(pessoa)
                .comEmissao(emissao).comEmitente(emitente).comNumeroCheque(numeroCheque)
                .comObservacao(observacao).comCotacao(cotacao).comTipoLancamento(tipoLancamento)
                .comTipoSituacao(SituacaoDeCheque.ABERTO).comValor(valor).comVencimento(vencimento)
                .construir();
    }

    public Titulo construirTitulo() throws DadoInvalidoException {
        return new TituloBuilder().comValor(valor).comSaldo(valor).comEmissao(emissao).comOperacaoFinanceira(unidadeFinanceira).comPessoa(pessoa)
                .comTipoFormaPagRec(TipoFormaPagRec.A_PRAZO).comCotacao(cotacao).comHistorico(observacao).comVencimento(vencimento).
                construir();
    }

    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ParcelaBV)) {
            return false;
        }
        ParcelaBV outro = (ParcelaBV) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
