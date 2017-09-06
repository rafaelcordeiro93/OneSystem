package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoFiscal;

public class GrupoFiscalDAO extends GenericDAO<GrupoFiscal> {

    public GrupoFiscalDAO() {
        super(GrupoFiscal.class);
        limpar();
    }

    public GrupoFiscalDAO porId(Long id) {
        where += " and grupoFiscal.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoFiscalDAO porNome(GrupoFiscal grupo) {
        where += " and grupoFiscal.nome = :pNome ";
        parametros.put("pNome", grupo.getNome());
        return this;
    }

}
