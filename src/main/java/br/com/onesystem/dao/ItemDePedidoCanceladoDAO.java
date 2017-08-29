package br.com.onesystem.dao;

import br.com.onesystem.domain.ItemDePedidoCancelado;
import br.com.onesystem.domain.ItemDePedido;

public class ItemDePedidoCanceladoDAO extends GenericDAO<ItemDePedidoCancelado> {

    public ItemDePedidoCanceladoDAO() {
        super(ItemDePedidoCancelado.class);
    }

    public ItemDePedidoCanceladoDAO porItemDePedido(ItemDePedido Item) {
        if (Item != null) {
            where += " and itemDePedidoCancelado.itemDePedido = :IitemDePedido ";
            parametros.put("IitemDePedido", Item);
        }
        return this;
    }

}
