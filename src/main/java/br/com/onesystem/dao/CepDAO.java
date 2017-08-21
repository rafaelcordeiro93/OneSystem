package br.com.onesystem.dao;

import br.com.onesystem.domain.Cep;

public class CepDAO extends GenericDAO<Cep> {

    public CepDAO() {
        super(Cep.class);
    }

    public CepDAO porCep(Cep cep)  {
        where += " and cep.nome = :cNome ";
        parametros.put("cNome", cep.getCep());
        return this;
    }

    public CepDAO porId(Long id) {
        where += " and cep.id = :cId ";
        parametros.put("cId", id);
        return this;
    }
}
