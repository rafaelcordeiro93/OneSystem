package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.GrupoDeMargem;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "grupoDeMargemService")
@ApplicationScoped
public class GrupoDeMargemService implements Serializable {
    
    public List<GrupoDeMargem> buscarGrupoDeMargens(){
        return new ArmazemDeRegistros<GrupoDeMargem>(GrupoDeMargem.class).listaTodosOsRegistros();
    }
    
}
