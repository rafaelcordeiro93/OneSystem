package br.com.onesystem.war.service;

import br.com.onesystem.dao.UnidadeMedidaItemDAO;
import br.com.onesystem.domain.UnidadeMedidaItem;
import java.io.Serializable;
import java.util.List;

public class UnidadeMedidaItemService implements Serializable {

    public List<UnidadeMedidaItem> buscarUnidadeMedidaItens() {
        return new UnidadeMedidaItemDAO().listaDeResultados();
    }

}
