package br.com.onesystem.dao;

import br.com.onesystem.domain.FaturaEmitida;

public class FaturaEmitidaDAO extends GenericDAO<FaturaEmitida> {

    public FaturaEmitidaDAO() {
        super(FaturaEmitida.class);
        limpar();
    }

    public FaturaEmitidaDAO porId(Long id) {
        if (id != null) {
            where += " and faturaEmitida.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

}
