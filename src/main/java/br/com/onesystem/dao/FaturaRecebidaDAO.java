package br.com.onesystem.dao;

import br.com.onesystem.domain.FaturaRecebida;

public class FaturaRecebidaDAO extends GenericDAO<FaturaRecebida> {

    public FaturaRecebidaDAO() {
        super(FaturaRecebida.class);
        limpar();
    }

    public FaturaRecebidaDAO porId(Long id) {
        if (id != null) {
            where += " and faturaRecebida.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

}
