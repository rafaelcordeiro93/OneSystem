package br.com.onesystem.dao;

import br.com.onesystem.domain.Cartao;

public class CartaoDAO extends GenericDAO<Cartao> {

    public CartaoDAO() {
        super(Cartao.class);
        limpar();
    }

    public CartaoDAO porNome(Cartao cartao) {
        where += " and cartao.nome = :bNome ";
        parametros.put("bNome", cartao.getNome());
        return this;
    }

    public CartaoDAO porId(Long id) {
        where += " and cartao.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
