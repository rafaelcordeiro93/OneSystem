package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.ContratoDeCambioDAO;
import br.com.onesystem.domain.ContratoDeCambio;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "contratoDeCambioService")
@ApplicationScoped
public class ContratoDeCambioService implements Serializable {

    public List<ContratoDeCambio> buscarContratosDeCambio() {
        return new ArmazemDeRegistros<ContratoDeCambio>(ContratoDeCambio.class).listaTodosOsRegistros();
    }

    public List<ContratoDeCambio> buscarContratosDeHoje() {
        return new ContratoDeCambioDAO().buscarContratosDeHoje();
    }

    public List<ContratoDeCambio> buscarContratosFechadosParaCambio() {
        return new ContratoDeCambioDAO().buscarContratosFechadosParaCambio();
    }

}
