package br.com.onesystem.war.service;

import br.com.onesystem.dao.PrivilegioDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Modulo;
import br.com.onesystem.domain.Privilegio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class PrivilegioService implements Serializable {
    
    @Inject
    private PrivilegioDAO dao;
    
    public List<Privilegio> buscarPrivilegioDoGrupo(GrupoDePrivilegio grupo){
        return dao.ePorGrupoDePrivilegio(grupo).listaDeResultados();
    }
    
    public List<Privilegio> buscarPrivilegioDoModulo(Modulo modulo){
        return dao.ePorModulo(modulo).listaDeResultados();
    }
        
}
