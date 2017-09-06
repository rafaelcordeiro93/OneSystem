package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.valueobjects.NaturezaFinanceira;

public class GrupoFinanceiroDAO extends GenericDAO<GrupoFinanceiro> {

    public GrupoFinanceiroDAO() {
        super(GrupoFinanceiro.class);
    }

    public GrupoFinanceiroDAO porId(Long id) {
        where += " and grupoFinanceiro.id = :gId ";
        parametros.put("gId", id);
        return this;
    }

    public GrupoFinanceiroDAO porNome(GrupoFinanceiro grupoFinanceiro) {
        where += " and grupoFinanceiro.nome = :pNome ";
        parametros.put("pNome", grupoFinanceiro.getNome());
        return this;
    }

    public GrupoFinanceiroDAO porReceitas() {
        where += " and grupoFinanceiro.naturezaFinanceira = :pReceitas ";
        parametros.put("pReceitas", NaturezaFinanceira.RECEITA);
        return this;
    }

    public GrupoFinanceiroDAO porDespesas() {
        where += " and grupoFinanceiro.naturezaFinanceira = :pDespesas ";
        parametros.put("pDespesas", NaturezaFinanceira.DESPESA);
        return this;
    }

}
