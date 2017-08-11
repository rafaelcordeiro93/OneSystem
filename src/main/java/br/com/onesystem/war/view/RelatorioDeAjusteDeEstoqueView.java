package br.com.onesystem.war.view;

import br.com.onesystem.dao.AjusteDeEstoqueDAO;
import br.com.onesystem.domain.Coluna;
import br.com.onesystem.war.service.impl.BasicMBReportImpl;
import java.io.Serializable;
import javax.inject.Named;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.PostConstruct;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeAjusteDeEstoqueView extends BasicMBReportImpl<AjusteDeEstoque> implements Serializable {

    @PostConstruct
    @Override
    protected void init() {
        
        Coluna id = new Coluna(bundle.getLabel("Id"), bundle.getLabel("Ajuste_De_Estoque"), "id", AjusteDeEstoque.class, Long.class, null);
        Coluna nomeDoItem = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Item") + ")", bundle.getLabel("Item"), "item", "nome", Item.class, String.class, null);
        Coluna nomeOperacao = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Operacao") + ")", bundle.getLabel("Operacao"), "operacao", "nome", Operacao.class, String.class, null);
        Coluna nomeDeposito = new Coluna(bundle.getLabel("Nome") + "(" + bundle.getLabel("Deposito") + ")", bundle.getLabel("Deposito"), "deposito", "nome", Deposito.class, String.class, null);
        Coluna quantidade = new Coluna(bundle.getLabel("Quantidade"), bundle.getLabel("Ajuste_De_Estoque"), "quantidade", AjusteDeEstoque.class, BigDecimal.class, null);
        Coluna emissao = new Coluna(bundle.getLabel("Emissao"), bundle.getLabel("Ajuste_De_Estoque"), "emissao", AjusteDeEstoque.class, Date.class, null);
        
        id.setTamanho(12);
        quantidade.setTamanho(15);
        emissao.setTamanho(12);
        
        addExtraClass(Operacao.class, "operacao");
        addExtraClass(Item.class, "item");
        addExtraClass(Deposito.class, "deposito");
        
        addCampoPadrao(id);
        addCampoPadrao(nomeDoItem);
        addCampoPadrao(nomeOperacao);
        addCampoPadrao(nomeDeposito);
        addCampoPadrao(quantidade);
        addCampoPadrao(emissao);

        initialize(AjusteDeEstoque.class, AjusteDeEstoqueDAO.class, TipoRelatorio.AJUSTE_DE_ESTOQUE);

    }
}
