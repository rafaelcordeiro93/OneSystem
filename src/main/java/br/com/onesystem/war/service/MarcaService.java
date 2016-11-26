package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Marca;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "marcaService")
@ApplicationScoped
public class MarcaService implements Serializable {
    
    public List<Marca> buscarMarcas(){
        return new ArmazemDeRegistros<Marca>(Marca.class).listaTodosOsRegistros();
    }
    
}
