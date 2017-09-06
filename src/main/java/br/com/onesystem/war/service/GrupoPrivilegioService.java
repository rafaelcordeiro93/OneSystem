package br.com.onesystem.war.service;

import br.com.onesystem.dao.GrupoDePrivilegioDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import java.io.Serializable;
import java.util.List;

public class GrupoPrivilegioService implements Serializable {

    public List<GrupoDePrivilegio> buscarGrupoDePrivilegio() {
         return new GrupoDePrivilegioDAO().listaDeResultados();
    }

}
