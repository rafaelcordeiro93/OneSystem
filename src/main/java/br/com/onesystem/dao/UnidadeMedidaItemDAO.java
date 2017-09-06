package br.com.onesystem.dao;

import br.com.onesystem.domain.UnidadeMedidaItem;

public class UnidadeMedidaItemDAO extends GenericDAO<UnidadeMedidaItem>{

    public UnidadeMedidaItemDAO() {
        super(UnidadeMedidaItem.class);
    }

    public UnidadeMedidaItemDAO porId(Long id) {
        where += " and unidadeMedidaItem.id = :dId ";
        parametros.put("dId", id);
        return this;
    }

    public UnidadeMedidaItemDAO porNome(UnidadeMedidaItem unidadeMedidaItem) {
        where += " and unidadeMedidaItem.nome = :dNome ";
        parametros.put("dNome", unidadeMedidaItem.getNome());
        return this;
    }

}
