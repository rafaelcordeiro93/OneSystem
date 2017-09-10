package br.com.onesystem.war.service;

import br.com.onesystem.dao.CambioDAO;
import br.com.onesystem.domain.Cambio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class CambioService implements Serializable {
    
    @Inject
    private CambioDAO dao;
    
    public List<Cambio> buscarCambios(){
        return dao.listaDeResultados();
    }
    
}
