package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Coluna;
import java.io.Serializable;
import java.util.List;

public class ColunaService implements Serializable {

    public List<Coluna> buscarColuna() {
        return new ArmazemDeRegistros<Coluna>(Coluna.class).listaTodosOsRegistros();
    }

}
