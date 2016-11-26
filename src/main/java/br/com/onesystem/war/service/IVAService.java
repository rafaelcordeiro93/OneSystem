package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.IVA;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "ivaService")
@ApplicationScoped
public class IVAService implements Serializable {
    
    public List<IVA> buscarIVAs(){
        return new ArmazemDeRegistros<IVA>(IVA.class).listaTodosOsRegistros();
    }
    
}
