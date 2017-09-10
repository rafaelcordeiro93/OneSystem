package br.com.onesystem.war.service;

import br.com.onesystem.dao.TabelaDeTributacaoDAO;
import br.com.onesystem.domain.TabelaDeTributacao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class TabelaDeTributacaoService implements Serializable {
    
    @Inject
    private TabelaDeTributacaoDAO dao;
    
    public List<TabelaDeTributacao> buscarTabelasDeTributacao(){
        return dao.listaDeResultados();
    }
    
}
