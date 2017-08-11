package br.com.onesystem.war.view;

import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import br.com.onesystem.valueobjects.Totalizador;
import java.io.Serializable;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.math.BigDecimal;
import java.util.Date;
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
        Coluna pessoa = new Coluna("Nome (Pessoa)", "Pessoa", "pessoa", "nome", Pessoa.class, String.class);
        pessoa.setTamanho(30);

        String item = bundle.getLabel("Item");

        addExtraClass(Marca.class, "marca");
        addExtraClass(Grupo.class, "grupo");
        addExtraClass(GrupoFiscal.class, "grupoFiscal");

        addCampoPadrao(new Coluna(bundle.getLabel("Id"), item, "id", Item.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome"), item, "nome", Item.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Saldo"), item, "saldo", Item.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Custo_Medio"), item, "custoMedio", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.AVERAGE));
        addCampoPadrao(new Coluna(bundle.getLabel("Ultimo_Custo"), item, "ultimoCusto", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));
        addCampoPadrao(new Coluna(bundle.getLabel("Total"), item, "total", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA, Totalizador.SUM));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Marca") + ")", item, "nome", Marca.class, String.class));

        initialize(Item.class, ItemDAO.class, TipoRelatorio.ITEM);

    }
}
