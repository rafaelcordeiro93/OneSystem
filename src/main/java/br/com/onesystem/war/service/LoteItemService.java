package br.com.onesystem.war.service;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.LoteItemDAO;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.DetalhamentoDeItem;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.builder.LoteItemBV;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class LoteItemService implements Serializable {

    @Inject
    private LoteItemDAO dao;

    @Inject
    private AtualizaDAO<LoteItem> atualizaDAO;
    
    @Inject
    private EntityManager manager;

    public List<LoteItem> buscarLoteItem() {
        return dao.listaDeResultados(manager);
    }

    public LoteItem buscarLoteItemPorID(Long id) {
        return dao.porId(id).resultado();
    }

    public List<LoteItem> buscarLotesPorItem(Item item) {
        return dao.porItem(item).listaDeResultados(manager);
    }
    
    public List<LoteItem> buscarLotesAtivosPorItem(Item item) {
        return dao.porItem(item).porAtivo().listaDeResultados(manager);
    }

    public BigDecimal calculaQuantidade(BigDecimal valorAntigo, BigDecimal valorNovo) {
        if (valorAntigo.compareTo(valorNovo) == 1) {
            return valorAntigo.subtract(valorNovo).negate();
        } else if (valorNovo.compareTo(valorAntigo) == 1) {
            return valorNovo.subtract(valorAntigo);
        } else {
            return BigDecimal.ZERO;
        }
    }

}
