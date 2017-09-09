package br.com.onesystem.war.service;

import br.com.onesystem.dao.FilialDAO;
import br.com.onesystem.domain.Filial;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class FilialService implements Serializable {

    @Inject
    private FilialDAO dao;
    
    public List<Filial> buscarFiliais() {
        return dao.listaDeResultados();
    }
}
