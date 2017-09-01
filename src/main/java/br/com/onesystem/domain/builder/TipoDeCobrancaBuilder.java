/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class TipoDeCobrancaBuilder {

    private Long id;
    private Cobranca cobranca;
    private Movimento movimento;
    private BigDecimal valor;
    private BigDecimal juros;
    private BigDecimal multa;
    private BigDecimal desconto;
    private String historico;
    private Cotacao cotacao;
    private Conta conta;

    public TipoDeCobrancaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public TipoDeCobrancaBuilder comCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
        return this;
    }

    public TipoDeCobrancaBuilder comMovimento(Movimento movimento) {
        this.movimento = movimento;
        return this;
    }

    public TipoDeCobrancaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public TipoDeCobrancaBuilder comJuros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public TipoDeCobrancaBuilder comMulta(BigDecimal multa) {
        this.multa = multa;
        return this;
    }

    public TipoDeCobrancaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public TipoDeCobrancaBuilder comObservacao(String historico) {
        this.historico = historico;
        return this;
    }

    public TipoDeCobrancaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public TipoDeCobrancaBuilder comConta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public TipoDeCobranca construir() throws DadoInvalidoException {
        return new TipoDeCobranca(id, cobranca, movimento, valor, juros, multa, desconto, historico, cotacao, conta);
    }

}
