package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Motivo;
import java.io.Serializable;
import java.util.List;

public class MotivoService implements Serializable {
    
    public List<Motivo> buscarMotivos(){
        return new ArmazemDeRegistros<Motivo>(Motivo.class).listaTodosOsRegistros();
    }
    
}
