package br.com.onesystem.dao;

import br.com.onesystem.domain.Comissao;

public class ComissaoDAO extends GenericDAO<Comissao> {

    public ComissaoDAO() {
        super(Comissao.class);
        limpar();
    }

    public ComissaoDAO porId(Long id) {
        where += " and comissao.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

    public ComissaoDAO porNome(Comissao comissao) {
        where += " and comissao.nome = :cNome ";
        parametros.put("cNome", comissao.getNome());
        return this;
    }

}
