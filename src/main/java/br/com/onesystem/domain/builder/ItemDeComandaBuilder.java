/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDeComandaBuilder {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;

    public ItemDeComandaBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemDeComandaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemDeComandaBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }

    public ItemDeComandaBuilder comQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ItemDeComanda construir() throws DadoInvalidoException {
        return new ItemDeComanda(id, item, unitario, quantidade);
    }

}
