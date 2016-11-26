package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.GrupoDePrivilegio;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "grupoPrivilegioService")
@ApplicationScoped
public class GrupoPrivilegioService implements Serializable {
    
    public List<GrupoDePrivilegio> buscarGrupoDePrivilegio(){
        return new ArmazemDeRegistros<GrupoDePrivilegio>(GrupoDePrivilegio.class).listaTodosOsRegistros();
    }
        
}
