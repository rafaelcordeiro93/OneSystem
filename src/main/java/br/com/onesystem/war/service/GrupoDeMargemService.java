package br.com.onesystem.war.service;

import br.com.onesystem.dao.MargemDAO;
import br.com.onesystem.domain.Margem;
import java.io.Serializable;
import java.util.List;

public class GrupoDeMargemService implements Serializable {
    
    public List<Margem> buscarGrupoDeMargens(){
         return new MargemDAO().listaDeResultados();
    }
    
}
