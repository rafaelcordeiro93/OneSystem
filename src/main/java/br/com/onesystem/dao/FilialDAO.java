package br.com.onesystem.dao;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Filial;
import java.io.Serializable;

public class FilialDAO extends GenericDAO<Filial> implements Serializable {

    public FilialDAO() {
        super(Filial.class);
    }

    public FilialDAO porId(Long id) {
        if (id != null) {
            where += " and filial.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

}
