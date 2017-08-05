/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.FaturaRecebida;
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
    private FaturaEmitida faturaEmitida;
    private FaturaRecebida faturaRecebida;
    private ConhecimentoDeFrete conhecimentoDeFrete;

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

    public ValorPorCotacaoBuilder comFaturaEmitida(FaturaEmitida faturaEmitida) {
        this.faturaEmitida = faturaEmitida;
        return this;
    }

    public ValorPorCotacaoBuilder comFaturaRecebida(FaturaRecebida faturaRecebida) {
        this.faturaRecebida = faturaRecebida;
        return this;
    }

    public ValorPorCotacaoBuilder comConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        return this;
    }

    public ValorPorCotacao construir() throws DadoInvalidoException {
        return new ValorPorCotacao(id, cotacao, valor, faturaEmitida, faturaRecebida, conhecimentoDeFrete);
    }

}
