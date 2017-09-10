package br.com.onesystem.war.service;

import br.com.onesystem.dao.TipoReceitaDAO;
import br.com.onesystem.domain.TipoReceita;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class TipoReceitaService implements Serializable {
    
    @Inject
    private TipoReceitaDAO dao;
    
    public List<TipoReceita> buscarTiposDeReceita(){
        return dao.listaDeResultados();
    }
    
}
