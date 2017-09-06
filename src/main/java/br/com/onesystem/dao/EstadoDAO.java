package br.com.onesystem.dao;

import br.com.onesystem.domain.Estado;

public class EstadoDAO extends GenericDAO<Estado> {

    public EstadoDAO() {
        super(Estado.class);
        limpar();
    }

    public EstadoDAO porId(Long id) {
        where += " and estado.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

}
