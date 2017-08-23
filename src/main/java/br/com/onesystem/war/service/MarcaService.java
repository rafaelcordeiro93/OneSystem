package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Marca;
import java.io.Serializable;
import java.util.List;

public class MarcaService implements Serializable {

    public List<Marca> buscarMarcas() {
        return new ArmazemDeRegistros<Marca>(Marca.class).listaTodosOsRegistros();
    }

}
