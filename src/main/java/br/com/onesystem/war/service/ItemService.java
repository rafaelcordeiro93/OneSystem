package br.com.onesystem.war.service;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.Item;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ItemService implements Serializable {

    @Inject
    private ItemDAO dao;
    
    public List<Item> buscarItems() {
        return dao.listaDeResultados();
    }

    public List<Item> buscarItemsRelatorio(Item item) {
        return dao.porItem(item).listaDeResultados();
    }

}
