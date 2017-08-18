/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.Pedido;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Cordeiro
 */
public class ItemDePedidoBuilder {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;
    private Pedido pedido;

    public ItemDePedidoBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemDePedidoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemDePedidoBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }

    public ItemDePedidoBuilder comQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }
    
      public ItemDePedidoBuilder comPedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public ItemDePedido construir() throws DadoInvalidoException {
        return new ItemDePedido(id, item, unitario, quantidade, pedido);
    }

}
