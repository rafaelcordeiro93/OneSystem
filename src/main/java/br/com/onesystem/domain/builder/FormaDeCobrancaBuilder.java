/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class FormaDeCobrancaBuilder {

    private Long id;
    private CobrancaVariavel cobranca;
    private Movimento movimento;
    private BigDecimal valor;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal desconto;
    private String observacao;
    private Cotacao cotacao;
    private Caixa caixa;
    private OperacaoFinanceira operacaoFinanceira;

    public FormaDeCobrancaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public FormaDeCobrancaBuilder comCobranca(CobrancaVariavel cobranca) {
        this.cobranca = cobranca;
        return this;
    }

    public FormaDeCobrancaBuilder comMovimento(Movimento movimento) {
        this.movimento = movimento;
        return this;
    }

    public FormaDeCobrancaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public FormaDeCobrancaBuilder comJuros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public FormaDeCobrancaBuilder comMulta(BigDecimal multa) {
        this.multa = multa;
        return this;
    }

    public FormaDeCobrancaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public FormaDeCobrancaBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public FormaDeCobrancaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public FormaDeCobrancaBuilder comCaixa(Caixa caixa) {
        this.caixa = caixa;
        return this;
    }

    public FormaDeCobrancaBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public FormaDeCobranca construir() throws DadoInvalidoException {
        return new FormaDeCobranca(id, cobranca, movimento, valor, juros, multa, desconto, observacao, cotacao, caixa, operacaoFinanceira);
    }

}
