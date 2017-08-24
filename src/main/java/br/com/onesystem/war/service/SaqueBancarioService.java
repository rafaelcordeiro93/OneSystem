package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.SaqueBancario;
import java.io.Serializable;
import java.util.List;

public class SaqueBancarioService implements Serializable {

    public List<SaqueBancario> buscarSaqueBancarios() {
        return new ArmazemDeRegistros<SaqueBancario>(SaqueBancario.class).listaTodosOsRegistros();
    }
}
