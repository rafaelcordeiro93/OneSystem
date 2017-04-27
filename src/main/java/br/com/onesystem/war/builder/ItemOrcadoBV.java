/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.builder.ItemOrcadoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemOrcadoBV {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;

    public ItemOrcadoBV() {
    }

    public ItemOrcadoBV(ItemOrcado item) {
        this.id = item.getId();
        this.item = item.getItem();
        this.unitario = item.getUnitario();
        this.quantidade = item.getQuantidade();
    }

    public Long getId() {
        return id;
    }

    public ItemOrcadoBV setId(Long id) {
        this.id = id;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getUnitario() {
        return unitario;
    }

    public void setUnitario(BigDecimal unitario) {
        this.unitario = unitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {

        return getQuantidade() == null ? BigDecimal.ZERO : getQuantidade().multiply(unitario == null ? BigDecimal.ZERO : unitario);
    }

    public ItemOrcado construir() throws DadoInvalidoException {
        return new ItemOrcadoBuilder().comItem(item).comUnitario(unitario).comQuantidade(quantidade).construir();
    }

    public ItemOrcado construirComId() throws DadoInvalidoException {
        return new ItemOrcadoBuilder().comId(id).comItem(item).comUnitario(unitario).comQuantidade(quantidade).construir();
    }

}
