/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDeCondicionalBuilder {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;
    private LoteItem loteItem;

    public ItemDeCondicionalBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemDeCondicionalBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemDeCondicionalBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }

    public ItemDeCondicionalBuilder comQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ItemDeCondicionalBuilder comLote(LoteItem loteItem) {
        this.loteItem = loteItem;
        return this;
    }

    public ItemDeCondicional construir() throws DadoInvalidoException {
        return new ItemDeCondicional(id, item, unitario, quantidade, loteItem);
    }

}
