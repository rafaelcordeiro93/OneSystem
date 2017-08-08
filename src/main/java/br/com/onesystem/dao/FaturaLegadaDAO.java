package br.com.onesystem.dao;

import br.com.onesystem.domain.FaturaLegada;

public class FaturaLegadaDAO extends GenericDAO<FaturaLegada> {

    public FaturaLegadaDAO() {
        super(FaturaLegada.class);
        limpar();
    }

    public FaturaLegadaDAO porId(Long id) {
        where += " and faturaLegada.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
