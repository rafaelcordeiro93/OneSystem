package br.com.onesystem.war.service;

import br.com.onesystem.dao.PaisDAO;
import br.com.onesystem.domain.Pais;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class PaisService implements Serializable {

    @Inject
    private PaisDAO dao;
    
    public List<Pais> buscarPais() {
        return dao.listaDeResultados();
    }

}
