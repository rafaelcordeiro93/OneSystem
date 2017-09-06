package br.com.onesystem.dao;

import br.com.onesystem.domain.Cidade;

public class CidadeDAO extends GenericDAO<Cidade> {

    public CidadeDAO() {
        super(Cidade.class);
        limpar();
    }

    public CidadeDAO porNome(Cidade cidade) {
        where += " and cidade.nome = :cNome ";
        parametros.put("cNome", cidade.getNome());
        return this;
    }

    public CidadeDAO porId(Long id) {
        where += " and cidade.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

}
