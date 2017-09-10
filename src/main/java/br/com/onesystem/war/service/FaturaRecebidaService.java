package br.com.onesystem.war.service;

import br.com.onesystem.dao.FaturaRecebidaDAO;
import br.com.onesystem.domain.FaturaRecebida;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class FaturaRecebidaService implements Serializable {

    @Inject
    private FaturaRecebidaDAO dao;
    
    public List<FaturaRecebida> buscarFaturasEmitidas() {
        return dao.listaDeResultados();
    }

}
