package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Margem;
import java.io.Serializable;
import java.util.List;

public class MargemService implements Serializable {

    public List<Margem> buscarMargems() {
        return new ArmazemDeRegistros<Margem>(Margem.class).listaTodosOsRegistros();
    }

}
