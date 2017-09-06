package br.com.onesystem.war.service;

import br.com.onesystem.dao.NotaRecebidaDAO;
import br.com.onesystem.domain.NotaRecebida;
import java.io.Serializable;
import java.util.List;

public class NotaRecebidaService implements Serializable {

    public List<NotaRecebida> buscarNotasRecebidas() {
        return new NotaRecebidaDAO().listaDeResultados();
    }

}
