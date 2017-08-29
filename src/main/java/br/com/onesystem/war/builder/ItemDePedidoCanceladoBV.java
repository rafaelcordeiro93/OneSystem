/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ItemDePedidoCancelado;
import br.com.onesystem.domain.Motivo;
import br.com.onesystem.domain.Pedido;
import br.com.onesystem.domain.builder.ItemDePedidoCanceladoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Cordeiro
 */
public class ItemDePedidoCanceladoBV implements Serializable, BuilderView<ItemDePedidoCancelado> {

    private Long id;
    private ItemDePedido itemDePedido;
    private Date dataCancelamento;
    private Motivo motivo;
    private Pedido pedido;
    private BigDecimal quantidade;

    public ItemDePedidoCanceladoBV() {
    }

    public ItemDePedidoCanceladoBV(ItemDePedidoCancelado item) {
        this.id = item.getId();
        this.itemDePedido = item.getItemDePedido();
        this.dataCancelamento = item.getDataCancelamento();
        this.quantidade = item.getQuantidade();
        this.pedido = item.getPedido();
        this.motivo = item.getMotivo();
    }

    public Long getId() {
        return id;
    }

    public ItemDePedidoCanceladoBV setId(Long id) {
        this.id = id;
        return this;
    }

    public ItemDePedido getItemDePedido() {
        return itemDePedido;
    }

    public void setItemDePedido(ItemDePedido itemDePedido) {
        this.itemDePedido = itemDePedido;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public ItemDePedidoCancelado construir() throws DadoInvalidoException {
        return new ItemDePedidoCanceladoBuilder().comItemDePedido(itemDePedido).comQuantidade(quantidade)
                .comDataCancelamento(dataCancelamento).comMotivo(motivo).comPedido(pedido).construir();
    }

    @Override
    public ItemDePedidoCancelado construirComID() throws DadoInvalidoException {
        return new ItemDePedidoCanceladoBuilder().comId(id).comItemDePedido(itemDePedido).comQuantidade(quantidade)
                .comDataCancelamento(dataCancelamento).comMotivo(motivo).comPedido(pedido).construir();
    }

}
