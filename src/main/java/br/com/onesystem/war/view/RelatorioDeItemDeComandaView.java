package br.com.onesystem.war.view;

import br.com.onesystem.dao.ItemDeComandaDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.io.Serializable;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeItemDeComandaView extends BasicMBReportImpl<ItemDeComanda> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        String item = bundle.getLabel("Item");
        String itemDeComanda = bundle.getLabel("Item_Comanda");

        addExtraClass(Item.class, "item");
        addExtraClass(Grupo.class, "item.grupo");
        addExtraClass(GrupoFiscal.class, "item.grupoFiscal");

        addCampoPadrao(new Coluna(bundle.getLabel("Id") + "(" + item + ")", item, "id", Item.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + item + ")", item, "item", "nome", Item.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Marca") + ")", bundle.getLabel("Marca"), "item", "marca", "nome", Marca.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Quantidade"), itemDeComanda, "quantidade", ItemDeComanda.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Unitario"), itemDeComanda, "unitario", ItemDeComanda.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));
        addCampoPadrao(new Coluna(bundle.getLabel("Total"), itemDeComanda, "total", ItemDeComanda.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));

        initialize(ItemDeComanda.class, ItemDeComandaDAO.class, TipoRelatorio.ITEM_COMANDA);
    }

}
