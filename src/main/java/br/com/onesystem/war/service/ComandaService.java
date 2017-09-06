package br.com.onesystem.war.service;

import br.com.onesystem.dao.ComandaDAO;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.valueobjects.EstadoDeComanda;
import java.io.Serializable;
import java.util.List;

public class ComandaService implements Serializable {

    public List<Comanda> buscarComandas() {
        return new ComandaDAO().listaDeResultados();
    }

    public List<Comanda> buscarComandasNo(EstadoDeComanda estadoDeComanda) {
        return new ComandaDAO().porEstado(estadoDeComanda).listaDeResultados();
    }

}
