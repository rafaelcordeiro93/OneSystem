package br.com.onesystem.war.service;

import br.com.onesystem.dao.FilialDAO;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Filial;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class FilialService implements Serializable {

    @Inject
    private FilialDAO dao;
    
    @Inject 
    private EntityManager manager;
    
    public List<Filial> buscarFiliais() {
        return dao.listaDeResultados(manager);
    }
    
}
