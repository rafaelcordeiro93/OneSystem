/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.builder.TipoDeCobrancaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class TipoDeCobrancaBV implements BuilderView<TipoDeCobranca> {

    private Long id;
    private Cobranca cobranca;
    private CobrancaFixa cobrancaFixa;
    private Recebimento recebimento;
    private Pagamento pagamento;
    private BigDecimal valor;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal desconto;
    private String observacao;
    private Cotacao cotacao;
    private Conta conta;
    private Date dataCompensacao;

    public TipoDeCobrancaBV() {
    }

    public TipoDeCobrancaBV(TipoDeCobranca f) {
        this.id = f.getId();
        this.cobranca = f.getCobranca();
        this.recebimento = f.getRecebimento();
        this.valor = f.getValor();
        this.juros = f.getJuros();
        this.multa = f.getMulta();
        this.desconto = f.getDesconto();
        this.observacao = f.getObservacao();
        this.cotacao = f.getCotacao();
        this.conta = f.getConta();
        this.pagamento = f.getPagamento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Recebimento getRecebimento() {
        return recebimento;
    }

    public void setRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getMulta() {
        return multa;
    }

    public void setMulta(BigDecimal multa) {
        this.multa = multa;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public CobrancaFixa getCobrancaFixa() {
        return cobrancaFixa;
    }

    public void setCobrancaFixa(CobrancaFixa cobrancaFixa) {
        this.cobrancaFixa = cobrancaFixa;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public void setDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
    }

    public TipoDeCobranca construir() throws DadoInvalidoException {
        return new TipoDeCobrancaBuilder().comCobranca(cobranca).comCotacao(cotacao).comDesconto(desconto)
                .comObservacao(observacao).comJuros(juros).comMulta(multa).comRecebimento(recebimento).comValor(valor)
                .comCobrancaFixa(cobrancaFixa).comConta(conta).comPagamento(pagamento).construir();
    }

    @Override
    public TipoDeCobranca construirComID() throws DadoInvalidoException {
        return new TipoDeCobrancaBuilder().comId(id).comCobranca(cobranca).comCotacao(cotacao).comDesconto(desconto)
                .comObservacao(observacao).comJuros(juros).comMulta(multa).comRecebimento(recebimento).comValor(valor)
                .comCobrancaFixa(cobrancaFixa).comConta(conta).comPagamento(pagamento).construir();
    }

}
