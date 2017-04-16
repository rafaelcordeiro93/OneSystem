package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.IVA;
import java.io.Serializable;
import java.util.List;

public class IVAService implements Serializable {
    
    public List<IVA> buscarIVAs(){
        return new ArmazemDeRegistros<IVA>(IVA.class).listaTodosOsRegistros();
    }
    
}
