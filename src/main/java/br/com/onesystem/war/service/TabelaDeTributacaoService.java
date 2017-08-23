package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.TabelaDeTributacao;
import java.io.Serializable;
import java.util.List;

public class TabelaDeTributacaoService implements Serializable {
    
    public List<TabelaDeTributacao> buscarTabelasDeTributacao(){
        return new ArmazemDeRegistros<TabelaDeTributacao>(TabelaDeTributacao.class).listaTodosOsRegistros();
    }
    
}
