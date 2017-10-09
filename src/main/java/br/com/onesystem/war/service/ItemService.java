package br.com.onesystem.war.service;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.dao.ItemImagemDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemImagem;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.PrecoDeItem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

public class ItemService implements Serializable {

    @Inject
    private ItemDAO dao;

    @Inject
    private EstoqueService estoqueService;

    @Inject
    private ConfiguracaoEstoqueService configuracaoEstoqueService;

    @Inject
    private PrecoDeItemService precoDeItemService;

    @Inject
    private ItemImagemDAO itemImageDAO;

    public List<Item> buscarItems() {
        return dao.listaDeResultados();
    }

    public List<Item> buscarItemsRelatorio(Item item) {
        return dao.porItem(item).listaDeResultados();
    }
    
    public List<ItemImagem> buscarImagemDo(Item item) {
        return itemImageDAO.porItem(item).listaDeResultados();
    }

    public BigDecimal getPrecoTotal(Item item) {
        if (getSaldo(item).compareTo(BigDecimal.ZERO) == 0) {
            return getPreco(item);
        } else {
            return getSaldo(item).multiply(getPreco(item)).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getPreco(Item item, ListaDePreco listaDePreco) {
        PrecoDeItem preco = precoDeItemService.buscaListaDePrecoAtual(item, listaDePreco, new Date());
        if (preco == null) {
            return BigDecimal.ZERO;
        } else {
            return preco.getValor();
        }
    }

    public BigDecimal getPreco(Item item) {
        ConfiguracaoEstoque conf = configuracaoEstoqueService.buscar();
        if (conf != null && conf.getListaDePreco() != null) {
            PrecoDeItem preco = precoDeItemService.buscaListaDePrecoAtual(item, conf.getListaDePreco(), new Date());
            if (preco == null) {
                return BigDecimal.ZERO;
            } else {
                return preco.getValor();
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getCustoTotal(Item item) {
        if (getSaldo(item).compareTo(BigDecimal.ZERO) == 0) {
            return getPreco(item);
        } else {
            return getSaldo(item).multiply(getUltimoCusto(item)).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getSaldo(Item item) {
        return estoqueService.buscaSaldoTotalDeEstoque(item, new Date());
    }

    public BigDecimal getSaldo(Item item, Date data) {
        return estoqueService.buscaSaldoTotalDeEstoque(item, data);
    }

    public BigDecimal getUltimoCusto(Item item) {
        return estoqueService.buscaUltimoCustoItem(item, new Date());
    }

    public BigDecimal getCustoMedio(Item item) {
        return estoqueService.buscaCustoMedioDeItem(item, new Date());
    }

}
