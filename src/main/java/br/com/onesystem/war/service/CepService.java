package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cep;
import java.io.Serializable;
import java.util.List;

public class CepService implements Serializable {

    public List<Cep> buscarCeps() {
        return new ArmazemDeRegistros<Cep>(Cep.class).listaTodosOsRegistros();
    }

}
