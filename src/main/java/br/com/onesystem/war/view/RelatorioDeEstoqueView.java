package br.com.onesystem.war.view;

import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.valueobjects.TipoFormatacaoNumero;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.io.Serializable;
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
public class RelatorioDeEstoqueView extends BasicMBReportImpl<Estoque> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        String item = bundle.getLabel("Item");
        String estoque = bundle.getLabel("Estoque");

        addExtraClass(Item.class, "item");
        addExtraClass(OperacaoDeEstoque.class, "operacaoDeEstoque");
        addExtraClass(ContaDeEstoque.class, "operacaoDeEstoque.contaDeEstoque");
        addExtraClass(Deposito.class, "deposito");
        addExtraClass(ItemDeNota.class, "itemDeNota");
        addExtraClass(Nota.class, "itemDeNota.nota");
        addExtraClass(AjusteDeEstoque.class, "ajusteDeEstoque");
        addExtraClass(ItemDeCondicional.class, "itemDeCondicional");

        addCampoPadrao(new Coluna(bundle.getLabel("Id"), item, "item", "id", Item.class, Long.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome"), item, "item", "nome", Item.class, String.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Quantidade"), estoque, "quantidade", Estoque.class, BigDecimal.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Emissao"), estoque, "emissao", Estoque.class, Date.class));
        addCampoPadrao(new Coluna(bundle.getLabel("Ultimo_Custo"), item, "ultimoCusto", Item.class, BigDecimal.class, TipoFormatacaoNumero.MOEDA));
        addCampoPadrao(new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Marca") + ")", bundle.getLabel("Marca"), "marca", "nome", Marca.class, String.class));

        initialize(Estoque.class, EstoqueDAO.class, TipoRelatorio.ESTOQUE);

    }
}
