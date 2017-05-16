/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ValorPorCotacaoBuilder {

    private Long id;
    private Cotacao cotacao;
    private BigDecimal valor;

    public ValorPorCotacaoBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public ValorPorCotacaoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ValorPorCotacaoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ValorPorCotacao construir() throws DadoInvalidoException {
        return new ValorPorCotacao(id, cotacao, valor);
    }

}
