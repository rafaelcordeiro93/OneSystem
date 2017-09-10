package br.com.onesystem.war.service;

import br.com.onesystem.dao.UnidadeMedidaItemDAO;
import br.com.onesystem.domain.UnidadeMedidaItem;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class UnidadeMedidaItemService implements Serializable {

    @Inject
    private UnidadeMedidaItemDAO dao;
    
    public List<UnidadeMedidaItem> buscarUnidadeMedidaItens() {
        return dao.listaDeResultados();
    }

}
