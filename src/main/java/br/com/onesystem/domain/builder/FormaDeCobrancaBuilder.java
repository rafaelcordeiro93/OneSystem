/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class FormaDeCobrancaBuilder {

    private Long id;
    private Cobranca cobranca;
    private Recebimento recebimento;
    private BigDecimal valor;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal desconto;
    private String historico;
    private Cotacao cotacao;

    public FormaDeCobrancaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public FormaDeCobrancaBuilder comCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
        return this;
    }

    public FormaDeCobrancaBuilder comRecebimento(Recebimento recebimento) {
        this.recebimento = recebimento;
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

    public FormaDeCobrancaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public FormaDeCobrancaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public FormaDeCobranca construir() throws DadoInvalidoException {
        return new FormaDeCobranca(id, cobranca, recebimento, valor, juros, multa, desconto, historico, cotacao);
    }

}
