package br.com.onesystem.war.service;

import br.com.onesystem.dao.GrupoDAO;
import br.com.onesystem.domain.Grupo;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class GrupoService implements Serializable {
    
    @Inject
    private GrupoDAO dao;
    
    public List<Grupo> buscarGrupos(){
         return dao.listaDeResultados();
    }
    
}
