package br.com.onesystem.war.service;

import br.com.onesystem.dao.ContratoDeCambioDAO;
import br.com.onesystem.domain.ContratoDeCambio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ContratoDeCambioService implements Serializable {

    @Inject
    private ContratoDeCambioDAO dao;
    
    public List<ContratoDeCambio> buscarContratosDeCambio() {
        return dao.listaDeResultados();
    }

    public List<ContratoDeCambio> buscarContratosDeHoje() {
        return dao.buscarContratosDeHoje();
    }

    public List<ContratoDeCambio> buscarContratosFechadosParaCambio() {
        return dao.buscarContratosFechadosParaCambio();
    }

}
