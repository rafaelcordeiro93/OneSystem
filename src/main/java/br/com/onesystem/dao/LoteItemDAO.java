package br.com.onesystem.dao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.LoteItem;

public class LoteItemDAO extends GenericDAO<LoteItem> {

    public LoteItemDAO() {
        super(LoteItem.class);
        limpar();
    }

    public LoteItemDAO porId(Long id) {
        if (id != null) {
            where += " and loteItem.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

    public LoteItemDAO porItem(Item item) {
        if (item != null) {
            where += " and loteItem.item = :bItem ";
            parametros.put("bItem", item);
        }
        return this;
    }

    public LoteItemDAO porAtivo() {
        where += " and loteItem.ativo = true ";
        return this;
    }

}
