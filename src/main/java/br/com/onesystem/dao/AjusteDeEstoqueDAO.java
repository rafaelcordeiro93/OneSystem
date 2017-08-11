package br.com.onesystem.dao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Item;
import java.math.BigDecimal;

public class AjusteDeEstoqueDAO extends GenericDAO<AjusteDeEstoque> {

    public AjusteDeEstoqueDAO() {
        super(AjusteDeEstoque.class);
        limpar();
    }

    public AjusteDeEstoqueDAO buscaMediaDeCusto() {
        query = "select avg(ajusteDeEstoque.custo) from AjusteDeEstoque ajusteDeEstoque ";
        return this;
    }

    public AjusteDeEstoqueDAO porItem(Item item) {
        where += " and ajusteDeEstoque.item = :pItem ";
        parametros.put("pItem", item);
        return this;
    }

    public AjusteDeEstoqueDAO porId(Long id) {
        where += " and ajusteDeEstoque.id = :aId ";
        parametros.put("aId", id);
        return this;
    }

    public AjusteDeEstoqueDAO ordenadoPorEmissaoDecrescente() {
        order += " order by ajusteDeEstoque.emissao desc ";
        return this;
    }

    public AjusteDeEstoqueDAO porCustoMaiorQueZero() {
        where += " and ajusteDeEstoque.custo > :pCusto";
        parametros.put("pCusto", BigDecimal.ZERO);
        return this;
    }
}
