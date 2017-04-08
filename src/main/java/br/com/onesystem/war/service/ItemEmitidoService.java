package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ItemEmitido;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "itemEmitidoService")
@ApplicationScoped
public class ItemEmitidoService implements Serializable {

    public List<ItemEmitido> buscarItensEmitidos() {
        return new ArmazemDeRegistros<ItemEmitido>(ItemEmitido.class).listaTodosOsRegistros();
    }


}
