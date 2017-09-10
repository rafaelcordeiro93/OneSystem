package br.com.onesystem.war.service;

import br.com.onesystem.dao.ComandaDAO;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.valueobjects.EstadoDeComanda;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ComandaService implements Serializable {

    @Inject
    private ComandaDAO dao;
    
    public List<Comanda> buscarComandas() {
        return dao.listaDeResultados();
    }

    public List<Comanda> buscarComandasNo(EstadoDeComanda estadoDeComanda) {
        return dao.porEstado(estadoDeComanda).listaDeResultados();
    }

}
