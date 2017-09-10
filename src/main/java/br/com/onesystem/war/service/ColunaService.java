package br.com.onesystem.war.service;

import br.com.onesystem.dao.ColunaDAO;
import br.com.onesystem.domain.Coluna;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ColunaService implements Serializable {

    @Inject
    private ColunaDAO dao;
    
    public List<Coluna> buscarColuna() {
        return dao.listaDeResultados();
    }

}
