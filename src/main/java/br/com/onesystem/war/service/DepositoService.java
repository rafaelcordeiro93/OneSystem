package br.com.onesystem.war.service;

import br.com.onesystem.dao.DepositoDAO;
import br.com.onesystem.domain.Deposito;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class DepositoService implements Serializable {

    @Inject
    private DepositoDAO dao;

    @Inject
    private EntityManager manager;

    public List<Deposito> buscarDepositos() {
        return dao.listaDeResultados();
    }

    @Transactional
    public Deposito getDeposito(Long id) {
        Deposito dep = dao.porId(id).resultado(manager);
        dep.getFiliais().size();
        return dep;
    }

}
