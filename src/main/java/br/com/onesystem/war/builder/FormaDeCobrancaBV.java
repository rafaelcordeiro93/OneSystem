/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.builder.FormaDeCobrancaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class FormaDeCobrancaBV implements BuilderView<FormaDeCobranca> {

    private Long id;
    private Cobranca cobranca;
    private Recebimento recebimento;
    private BigDecimal valor;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal desconto;
    private String observacao;
    private Cotacao cotacao;

    public FormaDeCobrancaBV() {
    }

    public FormaDeCobrancaBV(FormaDeCobranca f) {
        this.id = f.getId();
        this.cobranca = f.getCobranca();
        this.recebimento = f.getRecebimento();
        this.valor = f.getValor();
        this.juros = f.getJuros();
        this.multa = f.getMulta();
        this.desconto = f.getDesconto();
        this.observacao = f.getObservacao();
        this.cotacao = f.getCotacao();
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

    public FormaDeCobranca construir() throws DadoInvalidoException {
        return new FormaDeCobrancaBuilder().comCobranca(cobranca).comCotacao(cotacao).comDesconto(desconto)
                .comObservacao(observacao).comJuros(juros).comMulta(multa).comRecebimento(recebimento).comValor(valor)
                .construir();
    }

    @Override
    public FormaDeCobranca construirComID() throws DadoInvalidoException {
        return new FormaDeCobrancaBuilder().comId(id).comCobranca(cobranca).comCotacao(cotacao).comDesconto(desconto)
                .comObservacao(observacao).comJuros(juros).comMulta(multa).comRecebimento(recebimento).comValor(valor)
                .construir();
    }

}
