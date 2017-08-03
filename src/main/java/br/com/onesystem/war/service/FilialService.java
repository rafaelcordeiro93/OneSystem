package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Filial;
import java.io.Serializable;
import java.util.List;

public class FilialService implements Serializable {

    public List<Filial> buscarFiliais() {
        return new ArmazemDeRegistros<Filial>(Filial.class).listaTodosOsRegistros();
    }
}
