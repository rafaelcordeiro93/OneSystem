package br.com.onesystem.war.service;

import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.domain.CobrancaVariavel;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateful;
import javax.inject.Inject;

public class CobrancaService implements Serializable {

    @Inject
    private CobrancaDAO dao;
    
    public List<CobrancaVariavel> buscarCobrancas() {
        return dao.listaDeResultados();
    }

}
