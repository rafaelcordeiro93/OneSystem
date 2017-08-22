/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.Pedido;
import br.com.onesystem.domain.builder.ItemDePedidoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDePedidoBV {

    private Long id;
    private Item item;
    private BigDecimal valorUnitario;
    private BigDecimal quantidade;
    private Pedido pedido;

    public ItemDePedidoBV() {
    }

    public ItemDePedidoBV(ItemDePedido item) {
        this.id = item.getId();
        this.item = item.getItem();
        this.valorUnitario = item.getValorUnitario();
        this.quantidade = item.getQuantidade();
        this.pedido = item.getPedido();
    }

    public Long getId() {
        return id;
    }

    public ItemDePedidoBV setId(Long id) {
        this.id = id;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

//    public String getTotalFormatado() {
//        if (comanda != null) {
//            return MoedaFormatter.format(comanda.getCotacao().getConta().getMoeda(), getTotal());
//        } else {
//            return NumberFormat.getNumberInstance().format(getTotal());
//        }
//    }
//
//    public String getUnitarioFormatado() {
//        if (comanda != null) {
//            return MoedaFormatter.format(comanda.getCotacao().getConta().getMoeda(), getValorUnitario());
//        } else {
//            return NumberFormat.getNumberInstance().format(getValorUnitario());
//        }
//    }
    public BigDecimal getTotal() {
        return getQuantidade() == null ? BigDecimal.ZERO : getQuantidade().multiply(valorUnitario == null ? BigDecimal.ZERO : valorUnitario);
    }

    public ItemDePedido construir() throws DadoInvalidoException {
        return new ItemDePedidoBuilder().comItem(item).comUnitario(valorUnitario).comQuantidade(quantidade).construir();
    }

    public ItemDePedido construirComId() throws DadoInvalidoException {
        return new ItemDePedidoBuilder().comId(id).comItem(item).comUnitario(valorUnitario).comQuantidade(quantidade).construir();
    }

}
