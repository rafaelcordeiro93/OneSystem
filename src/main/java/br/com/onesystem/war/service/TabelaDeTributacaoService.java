package br.com.onesystem.war.service;

import br.com.onesystem.dao.TabelaDeTributacaoDAO;
import br.com.onesystem.domain.TabelaDeTributacao;
import java.io.Serializable;
import java.util.List;

public class TabelaDeTributacaoService implements Serializable {
    
    public List<TabelaDeTributacao> buscarTabelasDeTributacao(){
        return new TabelaDeTributacaoDAO().listaDeResultados();
    }
    
}
