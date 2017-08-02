package br.com.onesystem.dao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Item;

public class AjusteDeEstoqueDAO extends GenericDAO<AjusteDeEstoque> {

    public AjusteDeEstoqueDAO() {
        super(AjusteDeEstoque.class);
        limpar();
    }

    public AjusteDeEstoqueDAO buscarAjustesDeEstoque() {
        where += "select ajusteDeEstoque from AjusteDeEstoque ajusteDeEstoque where ajusteDeEstoque.id != 0 ";
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

    public AjusteDeEstoqueDAO ordenadoPorEmissaoDescrescente() {
        where += " order by ajusteDeEstoque.emissao desc ";
        return this;
    }
}
