package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.valueobjects.EstadoDeCondicional;

public class CondicionalDAO extends GenericDAO<Condicional>{

    public CondicionalDAO() {
        super(Condicional.class);
    }

    public CondicionalDAO porId(Long id) {
        where += " and condicional.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public CondicionalDAO porEstado(EstadoDeCondicional estado) {
        where += " and condicional.estado = :oEstado ";
        parametros.put("oEstado", estado);
        return this;
    }

}
