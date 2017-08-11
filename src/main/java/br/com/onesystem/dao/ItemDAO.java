package br.com.onesystem.dao;

import br.com.onesystem.domain.Item;

public class ItemDAO extends GenericDAO<Item> {

    public ItemDAO() {
        super(Item.class);
    }

    public ItemDAO porItem(Item item) {
        if (item != null) {
            where += " and item.id = :iId ";
            parametros.put("iId", item.getId());
        }
        return this;
    }

    public ItemDAO porId(Long id) {
        if (id != null) {
            where += " and item.id = :iId ";
            parametros.put("iId", id);
        }
        return this;
    }

}
