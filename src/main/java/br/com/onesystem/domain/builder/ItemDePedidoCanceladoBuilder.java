/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ItemDePedidoCancelado;
import br.com.onesystem.domain.Motivo;
import br.com.onesystem.domain.Pedido;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Cordeiro
 */
public class ItemDePedidoCanceladoBuilder {

    private Long id;
    private ItemDePedido itemDePedido;
    private Date dataCancelamento;
    private Motivo motivo;
    private Pedido pedido;
    private BigDecimal quantidade;

    public ItemDePedidoCanceladoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemDePedidoCanceladoBuilder comItemDePedido(ItemDePedido itemDePedido) {
        this.itemDePedido = itemDePedido;
        return this;
    }

    public ItemDePedidoCanceladoBuilder comDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
        return this;
    }

    public ItemDePedidoCanceladoBuilder comMotivo(Motivo motivo) {
        this.motivo = motivo;
        return this;
    }

    public ItemDePedidoCanceladoBuilder comQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ItemDePedidoCanceladoBuilder comPedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public ItemDePedidoCancelado construir() throws DadoInvalidoException {
        return new ItemDePedidoCancelado(id, itemDePedido, dataCancelamento, quantidade, pedido, motivo);
    }

}
