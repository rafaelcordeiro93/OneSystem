package br.com.onesystem.dao;

import br.com.onesystem.domain.Filial;

public class FilialDAO extends GenericDAO<Filial> {

    public FilialDAO() {
        super(Filial.class);
        limpar();
    }

    public FilialDAO porId(Long id) {
        if (id != null) {
            where += " and filial.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

}
