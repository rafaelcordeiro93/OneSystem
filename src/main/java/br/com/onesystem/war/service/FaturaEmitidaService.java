package br.com.onesystem.war.service;

import br.com.onesystem.dao.FaturaEmitidaDAO;
import br.com.onesystem.domain.FaturaEmitida;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class FaturaEmitidaService implements Serializable {

    @Inject
    private FaturaEmitidaDAO dao;
    
    public List<FaturaEmitida> buscarFaturasEmitidas() {
        return dao.listaDeResultados();
    }

}
