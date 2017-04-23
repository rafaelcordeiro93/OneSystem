package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Janela;
import java.io.Serializable;
import java.util.List;

public class JanelaService implements Serializable {

    public List<Janela> buscarJanelas() {
        return new ArmazemDeRegistros<Janela>(Janela.class).listaTodosOsRegistros();
    }

}
