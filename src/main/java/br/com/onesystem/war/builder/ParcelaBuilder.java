/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ParcelaBuilder {

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
    private SituacaoDeCheque tipoSituacaoCheque;
    private BigDecimal multas;
    private BigDecimal juros;
    private BigDecimal descontos;
    private String emitente;
    private String observacao;
    private Cartao cartao;
    private Integer dias;
    private String codTransacao;
    private SituacaoDeCartao tipoSituacaoCartao;
    private Moeda moeda;
    private Cambio cambio;
    private Recepcao recepcao;

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

    public SituacaoDeCheque getTipoSituacaoCheque() {
        return tipoSituacaoCheque;
    }

    public void setTipoSituacaoCheque(SituacaoDeCheque tipoSituacaoCheque) {
        this.tipoSituacaoCheque = tipoSituacaoCheque;
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

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getCodTransacao() {
        return codTransacao;
    }

    public void setCodTransacao(String codTransacao) {
        this.codTransacao = codTransacao;
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

}
