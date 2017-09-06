package br.com.onesystem.war.service;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.Item;
import java.io.Serializable;
import java.util.List;

public class ItemService implements Serializable {

    public List<Item> buscarItems() {
        return new ItemDAO().listaDeResultados();
    }

    public List<Item> buscarItemsRelatorio(Item item) {
        return new ItemDAO().porItem(item).listaDeResultados();
    }

}
