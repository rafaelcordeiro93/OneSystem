package br.com.onesystem.dao;

import br.com.onesystem.domain.Coluna;

public class ColunaDAO extends GenericDAO<Coluna> {

    public ColunaDAO() {
        super(Coluna.class);
        limpar();
    }

    public ColunaDAO porNome(Coluna coluna) {
        where += " and a.nome = :pNome ";
        parametros.put("pNome", coluna.getNome());
        return this;
    }

}
