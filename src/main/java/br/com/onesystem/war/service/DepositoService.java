package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Deposito;
import java.io.Serializable;
import java.util.List;

public class DepositoService implements Serializable {

    public List<Deposito> buscarDepositos() {
        return new ArmazemDeRegistros<Deposito>(Deposito.class).listaTodosOsRegistros();
    }

}
