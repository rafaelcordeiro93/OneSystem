package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Item;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "itemService")
@ApplicationScoped
public class ItemService implements Serializable {
    
    public List<Item> buscarItems(){
        return new ArmazemDeRegistros<Item>(Item.class).listaTodosOsRegistros();
    }
    
}
