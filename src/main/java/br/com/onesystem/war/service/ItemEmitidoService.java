package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ItemDeNota;
import java.io.Serializable;
import java.util.List;

public class ItemEmitidoService implements Serializable {

    public List<ItemDeNota> buscarItensEmitidos() {
        return new ArmazemDeRegistros<ItemDeNota>(ItemDeNota.class).listaTodosOsRegistros();
    }


}
