package br.com.onesystem.war.service;

import br.com.onesystem.dao.DepositoDAO;
import br.com.onesystem.domain.Deposito;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class DepositoService implements Serializable {

    @Inject
    private DepositoDAO dao;
    
    public List<Deposito> buscarDepositos() {
         return dao.listaDeResultados();
    }

}
