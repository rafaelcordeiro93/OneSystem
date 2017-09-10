package br.com.onesystem.war.service;

import br.com.onesystem.dao.FaturaLegadaDAO;
import br.com.onesystem.domain.FaturaLegada;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class FaturaLegadaService implements Serializable {

    @Inject
    private FaturaLegadaDAO dao;
    
    public List<FaturaLegada> buscarFaturasLegadas() {
        return dao.listaDeResultados();
    }

}
