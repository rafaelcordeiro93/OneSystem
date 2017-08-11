package br.com.onesystem.war.view;

import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Marca;
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
public class RelatorioDeItemView extends BasicMBReportImpl<Item> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        String item = bundle.getLabel("Item");

        addExtraClass(Marca.class, "marca");
        addExtraClass(Grupo.class, "grupo");
        addExtraClass(GrupoFiscal.class, "grupoFiscal");

        addCampoPadrao(new Coluna(bundle.getLabel("Id"), item, "id", Item.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome"), item, "nome", Item.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Saldo"), item, "saldo", Item.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Preco"), item, "preco", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));
        addCampoPadrao(new Coluna(bundle.getLabel("Ultimo_Custo"), item, "ultimoCusto", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Marca") + ")", item, "marca", "nome", Marca.class, String.class));

        initialize(Item.class, ItemDAO.class, TipoRelatorio.ITEM);

    }
}
