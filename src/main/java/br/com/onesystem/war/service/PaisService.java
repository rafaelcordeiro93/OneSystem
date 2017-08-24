package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Pais;
import java.io.Serializable;
import java.util.List;

public class PaisService implements Serializable {

    public List<Pais> buscarPais() {
        return new ArmazemDeRegistros<Pais>(Pais.class).listaTodosOsRegistros();
    }

}
