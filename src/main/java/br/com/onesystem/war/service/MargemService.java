package br.com.onesystem.war.service;

import br.com.onesystem.dao.MargemDAO;
import br.com.onesystem.domain.Margem;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class MargemService implements Serializable {

    @Inject
    private MargemDAO dao;
    
    public List<Margem> buscarMargems() {
        return dao.listaDeResultados();
    }

}
