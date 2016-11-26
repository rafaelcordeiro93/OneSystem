package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.UnidadeMedidaItem;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "unidadeMedidaItemService")
@ApplicationScoped
public class UnidadeMedidaItemService implements Serializable {

    public List<UnidadeMedidaItem> buscarUnidadeMedidaItens() {
        return new ArmazemDeRegistros<UnidadeMedidaItem>(UnidadeMedidaItem.class).listaTodosOsRegistros();
    }

}
