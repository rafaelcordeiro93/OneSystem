package br.com.onesystem.war.service;

import br.com.onesystem.dao.TipoReceitaDAO;
import br.com.onesystem.domain.TipoReceita;
import java.io.Serializable;
import java.util.List;

public class TipoReceitaService implements Serializable {
    
    public List<TipoReceita> buscarTiposDeReceita(){
        return new TipoReceitaDAO().listaDeResultados();
    }
    
}
