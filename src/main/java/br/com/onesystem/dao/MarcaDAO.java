package br.com.onesystem.dao;

import br.com.onesystem.domain.Marca;

public class MarcaDAO extends GenericDAO<Marca> {

    public MarcaDAO() {
        super(Marca.class);
    }

    public MarcaDAO porId(Long id) {
        where += " and marca.id = :mId ";
        parametros.put("mId", id);
        return this;
    }

    public MarcaDAO porNome(Marca marca) {
        where += " and marca.nome = :mNome ";
        parametros.put("mNome", marca.getNome());
        return this;
    }

}
