package br.com.onesystem.war.service;

import br.com.onesystem.dao.NotaRecebidaDAO;
import br.com.onesystem.domain.NotaRecebida;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class NotaRecebidaService implements Serializable {

    @Inject
    private NotaRecebidaDAO dao;
    
    public List<NotaRecebida> buscarNotasRecebidas() {
        return dao.listaDeResultados();
    }

}
