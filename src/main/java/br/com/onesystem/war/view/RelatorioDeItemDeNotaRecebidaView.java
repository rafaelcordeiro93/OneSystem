package br.com.onesystem.war.view;

import br.com.onesystem.dao.GenericDAO;
import br.com.onesystem.dao.ItemDeNotaDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
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
public class RelatorioDeItemDeNotaRecebidaView extends BasicMBReportImpl<ItemDeNota> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        String item = bundle.getLabel("Item");
        String itemDeNota = bundle.getLabel("Item_De_Nota");

        addExtraClass(Item.class, "item");
        addExtraClass(Nota.class, "nota");
        addExtraClass(Operacao.class, "nota.operacao");
        addExtraClass(Marca.class, "item.marca");
        addExtraClass(Grupo.class, "item.grupo");
        addExtraClass(GrupoFiscal.class, "item.grupoFiscal");

        addCampoPadrao(new Coluna(bundle.getLabel("Id") + "(" + item + ")", item, "id", Item.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + item + ")", item, "item", "nome", Item.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Marca") + ")", bundle.getLabel("Marca"), "item", "marca", "nome", Marca.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Quantidade"), itemDeNota, "quantidade", ItemDeNota.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Unitario"), itemDeNota, "unitario", ItemDeNota.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));
        addCampoPadrao(new Coluna(bundle.getLabel("Total"), itemDeNota, "total", ItemDeNota.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));

        initialize(ItemDeNota.class, ItemDeNotaDAO.class, TipoRelatorio.ITEM_RECEBIDO);
    }

    @Override
    protected void alteraConsulta(GenericDAO gDao) {
        gDao = ((ItemDeNotaDAO) gDao).consultaItemRecebido();
    }
}
