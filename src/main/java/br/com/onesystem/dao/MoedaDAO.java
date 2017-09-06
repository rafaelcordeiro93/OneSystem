package br.com.onesystem.dao;

import br.com.onesystem.domain.Moeda;

public class MoedaDAO extends GenericDAO<Moeda> {

    public MoedaDAO() {
        super(Moeda.class);
    }

    public MoedaDAO porNome(Moeda moeda) {
        where += " and moeda.nome = :mNome ";
        parametros.put("mNome", moeda.getNome());
        return this;
    }

    public MoedaDAO porSigla(Moeda moeda) {
        where += " and moeda.sigla = :mSigla ";
        parametros.put("mSigla", moeda.getSigla());
        return this;
    }

    public MoedaDAO porId(Long id) {
        where += " and moeda.id = :mId ";
        parametros.put("mId", id);
        return this;
    }

}
