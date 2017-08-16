package br.com.onesystem.war.view;

import br.com.onesystem.dao.ItemDeCondicionalDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeCondicional;
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
public class RelatorioDeItemDeCondicionalView extends BasicMBReportImpl<ItemDeCondicional> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        String item = bundle.getLabel("Item");
        String itemDeCondicional = bundle.getLabel("Item_Condicional");

        addExtraClass(Item.class, "item");
        addExtraClass(Grupo.class, "item.grupo");
        addExtraClass(GrupoFiscal.class, "item.grupoFiscal");

        addCampoPadrao(new Coluna(bundle.getLabel("Id") + "(" + item + ")", item, "id", Item.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + item + ")", item, "item", "nome", Item.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Marca") + ")", bundle.getLabel("Marca"), "item", "marca", "nome", Marca.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Quantidade"), itemDeCondicional, "quantidade", ItemDeCondicional.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Unitario"), itemDeCondicional, "unitario", ItemDeCondicional.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));
        addCampoPadrao(new Coluna(bundle.getLabel("Total"), itemDeCondicional, "total", ItemDeCondicional.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));

        initialize(ItemDeCondicional.class, ItemDeCondicionalDAO.class, TipoRelatorio.ITEM_CONDICIONAL);
    }

}
