package br.com.onesystem.war.service;

import br.com.onesystem.dao.GrupoDAO;
import br.com.onesystem.domain.Grupo;
import java.io.Serializable;
import java.util.List;

public class GrupoService implements Serializable {
    
    public List<Grupo> buscarGrupos(){
         return new GrupoDAO().listaDeResultados();
    }
    
}
