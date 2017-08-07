package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cidade;
import java.io.Serializable;
import java.util.List;

public class CidadeService implements Serializable {

    public List<Cidade> buscarCidades() {
        return new ArmazemDeRegistros<Cidade>(Cidade.class).listaTodosOsRegistros();
    }

}
