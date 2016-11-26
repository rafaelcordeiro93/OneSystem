package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cambio;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "cambioService")
@ApplicationScoped
public class CambioService implements Serializable {
    
    public List<Cambio> buscarCambios(){
        return new ArmazemDeRegistros<Cambio>(Cambio.class).listaTodosOsRegistros();
    }
    
}
