/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemImagem;

/**
 *
 * @author Rafael
 */
public class ItemImagemDAO extends GenericDAO<ItemImagem> {

    public ItemImagemDAO() {
        super(ItemImagem.class);
    }

    public ItemImagemDAO porId(Long id) {
        where += " and itemImagem.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public ItemImagemDAO porItem(Item item) {
        where += " and itemImagem.item = :pItem ";
        parametros.put("pItem", item);
        return this;
    }

}
