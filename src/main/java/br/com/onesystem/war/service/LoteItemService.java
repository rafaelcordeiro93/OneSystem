package br.com.onesystem.war.service;

import br.com.onesystem.dao.LoteItemDAO;
import br.com.onesystem.domain.LoteItem;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class LoteItemService implements Serializable {

    @Inject
    private LoteItemDAO dao;

    public List<LoteItem> buscarLoteItem() {
        return dao.listaDeResultados();
    }

}
