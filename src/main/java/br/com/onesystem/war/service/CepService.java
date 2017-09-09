package br.com.onesystem.war.service;

import br.com.onesystem.dao.CepDAO;
import br.com.onesystem.domain.Cep;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class CepService implements Serializable {

    @Inject
    private CepDAO dao;
    
    public List<Cep> buscarCeps() {
        return dao.listaDeResultados();
    }

}
