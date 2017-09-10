package br.com.onesystem.war.service;

import br.com.onesystem.dao.GrupoFiscalDAO;
import br.com.onesystem.domain.GrupoFiscal;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class GrupoFiscalService implements Serializable {
    
    @Inject
    private GrupoFiscalDAO dao;
    
    public List<GrupoFiscal> buscarGrupoFiscais(){
         return dao.listaDeResultados();
    }
    
}
