package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.LancamentoBancario;
import java.io.Serializable;
import java.util.List;

public class LancamentoBancarioService implements Serializable {

    public List<LancamentoBancario> buscarLancamentoBancarios() {
        return new ArmazemDeRegistros<LancamentoBancario>(LancamentoBancario.class).listaTodosOsRegistros();
    }
}
