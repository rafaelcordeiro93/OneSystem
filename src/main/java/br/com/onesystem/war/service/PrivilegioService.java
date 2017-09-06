package br.com.onesystem.war.service;

import br.com.onesystem.dao.PrivilegioDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Privilegio;
import java.io.Serializable;
import java.util.List;

public class PrivilegioService implements Serializable {
    
    public List<Privilegio> buscarPrivilegioDoGrupo(GrupoDePrivilegio grupo){
        return new PrivilegioDAO().ePorGrupoDePrivilegio(grupo).listaDeResultados();
    }
    
    public List<Privilegio> buscarPrivilegioDoModulo(Modulo modulo){
        return new PrivilegioDAO().ePorModulo(modulo).listaDeResultados();
    }
        
}
