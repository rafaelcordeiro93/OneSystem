/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemOrcadoBuilder {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;

    public ItemOrcadoBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemOrcadoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemOrcadoBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }

    public ItemOrcadoBuilder comQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ItemOrcado construir() throws DadoInvalidoException {
        return new ItemOrcado(id, item, unitario, quantidade);
    }

}
