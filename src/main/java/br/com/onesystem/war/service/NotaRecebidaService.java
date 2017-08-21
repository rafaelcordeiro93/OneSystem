package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.NotaRecebida;
import java.io.Serializable;
import java.util.List;

public class NotaRecebidaService implements Serializable {

    public List<NotaRecebida> buscarNotasRecebidas() {
        return new ArmazemDeRegistros<>(NotaRecebida.class).listaTodosOsRegistros();
    }

}
