package br.com.onesystem.dao;

import br.com.onesystem.domain.Comanda;
import br.com.onesystem.valueobjects.EstadoDeComanda;

public class ComandaDAO extends GenericDAO<Comanda> {

    public ComandaDAO() {
        super(Comanda.class);
    }

    public ComandaDAO porId(Long id) {
        where += " and comanda.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public ComandaDAO porEstado(EstadoDeComanda estado) {
        where += " and comanda.estado = :oEstado ";
        parametros.put("oEstado", estado);
        return this;
    }

}
