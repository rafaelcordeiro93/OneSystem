/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.builder.FormaDeCobrancaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class FormaDeCobrancaBV implements BuilderView<FormaDeCobranca> {

    private Long id;
    private CobrancaVariavel cobranca;
    private Movimento movimento;
    private BigDecimal valor;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal desconto;
    private String observacao;
    private Cotacao cotacao;
    private OperacaoFinanceira operacaoFinanceira;
    private Date dataCompensacao;

    public FormaDeCobrancaBV() {
    }

    public FormaDeCobrancaBV(FormaDeCobranca f) {
        this.id = f.getId();
        this.cobranca = f.getCobranca();
        this.movimento = f.getMovimento();
        this.valor = f.getValor();
        this.juros = f.getJuros();
        this.multa = f.getMulta();
        this.desconto = f.getDesconto();
        this.observacao = f.getObservacao();
        this.cotacao = f.getCotacao();
        this.operacaoFinanceira = f.getOperacaoFinanceira();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CobrancaVariavel getCobranca() {
        return cobranca;
    }

    public void setCobranca(CobrancaVariavel cobranca) {
        this.cobranca = cobranca;
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

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public void setDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
    }

    public Movimento getMovimento() {
        return movimento;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }
    
    public FormaDeCobranca construir() throws DadoInvalidoException {
        return new FormaDeCobrancaBuilder().comCobranca(cobranca).comCotacao(cotacao).comDesconto(desconto)
                .comObservacao(observacao).comJuros(juros).comMulta(multa).comMovimento(movimento).comValor(valor)
                .comOperacaoFinanceira(operacaoFinanceira).construir();
    }

    @Override
    public FormaDeCobranca construirComID() throws DadoInvalidoException {
        return new FormaDeCobrancaBuilder().comId(id).comCobranca(cobranca).comCotacao(cotacao).comDesconto(desconto)
                .comObservacao(observacao).comJuros(juros).comMulta(multa).comMovimento(movimento).comValor(valor)
                .comOperacaoFinanceira(operacaoFinanceira).construir();
    }

}
