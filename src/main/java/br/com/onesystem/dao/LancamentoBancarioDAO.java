package br.com.onesystem.dao;

import br.com.onesystem.domain.LancamentoBancario;

public class LancamentoBancarioDAO extends GenericDAO<LancamentoBancario> {

    public LancamentoBancarioDAO() {
        super(LancamentoBancario.class);
        limpar();
    }

    public LancamentoBancarioDAO porId(Long id) {
        where += " and lancamentoBancario.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
