package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoDePrivilegio;

public class GrupoDePrivilegioDAO extends GenericDAO<GrupoDePrivilegio> {

    public GrupoDePrivilegioDAO() {
        super(GrupoDePrivilegio.class);
        limpar();
    }

    public GrupoDePrivilegioDAO porId(Long id) {
        where += " and grupoDePrivilegio.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoDePrivilegioDAO porNome(GrupoDePrivilegio grupoDePrivilegio) {
        where += " and grupoDePrivilegio.nome = :pNome ";
        parametros.put("pNome", grupoDePrivilegio.getNome());
        return this;
    }

}
