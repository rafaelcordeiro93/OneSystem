package br.com.onesystem.dao;

import br.com.onesystem.domain.Grupo;

public class GrupoDAO extends GenericDAO<Grupo> {

    public GrupoDAO() {
        super(Grupo.class);
        limpar();
    }

    public GrupoDAO porId(Long id) {
        where += " and grupo.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoDAO porNome(Grupo grupo) {
        where += " and grupo.nome = :pNome ";
        parametros.put("pNome", grupo.getNome());
        return this;
    }

}
