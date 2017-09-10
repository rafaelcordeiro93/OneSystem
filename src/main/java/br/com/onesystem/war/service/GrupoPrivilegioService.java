package br.com.onesystem.war.service;

import br.com.onesystem.dao.GrupoDePrivilegioDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class GrupoPrivilegioService implements Serializable {

    @Inject
    private GrupoDePrivilegioDAO dao;
    
    public List<GrupoDePrivilegio> buscarGrupoDePrivilegio() {
         return dao.listaDeResultados();
    }

}
