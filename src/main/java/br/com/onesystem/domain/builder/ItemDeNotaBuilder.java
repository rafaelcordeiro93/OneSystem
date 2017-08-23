/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDeNotaBuilder {

    private Long id;
    private Item item;
    private BigDecimal unitario = BigDecimal.ZERO;
    private Nota nota;
    private List<QuantidadeDeItemPorDeposito> listaDeQuantidade;

    public ItemDeNotaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemDeNotaBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemDeNotaBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }

    public ItemDeNotaBuilder comNota(Nota nota) {
        this.nota = nota;
        return this;
    }

    public ItemDeNotaBuilder comListaDeQuantidade(List<QuantidadeDeItemPorDeposito> listaDeQuantidade) {
        this.listaDeQuantidade = listaDeQuantidade;
        return this;
    }

    public ItemDeNota construir() throws DadoInvalidoException {
        return new ItemDeNota(id, item, unitario, listaDeQuantidade);
    }

}
