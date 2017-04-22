package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.TipoReceita;
import java.io.Serializable;
import java.util.List;

public class ReceitaService implements Serializable {
    
    public List<TipoReceita> buscarReceitas(){
        return new ArmazemDeRegistros<TipoReceita>(TipoReceita.class).listaTodosOsRegistros();
    }
    
}
