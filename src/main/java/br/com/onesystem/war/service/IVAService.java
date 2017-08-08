package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.TabelaDeTributacao;
import java.io.Serializable;
import java.util.List;

public class IVAService implements Serializable {
    
    public List<TabelaDeTributacao> buscarIVAs(){
        return new ArmazemDeRegistros<TabelaDeTributacao>(TabelaDeTributacao.class).listaTodosOsRegistros();
    }
    
}
