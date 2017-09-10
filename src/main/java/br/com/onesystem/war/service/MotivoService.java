package br.com.onesystem.war.service;

import br.com.onesystem.dao.MotivoDAO;
import br.com.onesystem.domain.Motivo;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class MotivoService implements Serializable {

    @Inject
    private MotivoDAO dao;
    
    public List<Motivo> buscarMotivos() {
        return dao.listaDeResultados();
    }

}
