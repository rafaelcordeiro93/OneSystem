package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.NotaEmitida;
import java.io.Serializable;
import java.util.List;

public class NotaEmitidaService implements Serializable {

    public List<NotaEmitida> buscarNotasEmitidas() {
        return new ArmazemDeRegistros<>(NotaEmitida.class).listaTodosOsRegistros();
    }

}
