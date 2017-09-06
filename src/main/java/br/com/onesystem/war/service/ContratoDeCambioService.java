package br.com.onesystem.war.service;

import br.com.onesystem.dao.ContratoDeCambioDAO;
import br.com.onesystem.domain.ContratoDeCambio;
import java.io.Serializable;
import java.util.List;

public class ContratoDeCambioService implements Serializable {

    public List<ContratoDeCambio> buscarContratosDeCambio() {
        return new ContratoDeCambioDAO().listaDeResultados();
    }

    public List<ContratoDeCambio> buscarContratosDeHoje() {
        return new ContratoDeCambioDAO().buscarContratosDeHoje();
    }

    public List<ContratoDeCambio> buscarContratosFechadosParaCambio() {
        return new ContratoDeCambioDAO().buscarContratosFechadosParaCambio();
    }

}
