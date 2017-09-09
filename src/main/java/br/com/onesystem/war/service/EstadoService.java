package br.com.onesystem.war.service;

import br.com.onesystem.dao.EstadoDAO;
import br.com.onesystem.domain.Estado;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class EstadoService implements Serializable {

    @Inject
    private EstadoDAO dao;

    public List<Estado> buscarEstados() {
        return dao.listaDeResultados();
    }

}
