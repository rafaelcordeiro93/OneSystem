/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ComandaBuilder {

    private Long id;
    private List<ItemDeComanda> itensDeComanda;
    private ListaDePreco listaDePreco;
    private Cotacao cotacao;
    private String historico;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;

    public ComandaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ComandaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public ComandaBuilder comItensDeComanda(List<ItemDeComanda> itensDeComanda) {
        this.itensDeComanda = itensDeComanda;
        return this;
    }

    public ComandaBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public ComandaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public ComandaBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public ComandaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public ComandaBuilder comDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
        return this;
    }

    public ComandaBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }

    public Comanda construir() throws DadoInvalidoException {
        return new Comanda(id, listaDePreco, cotacao, itensDeComanda, historico, desconto, acrescimo, despesaCobranca, frete);
    }

}
