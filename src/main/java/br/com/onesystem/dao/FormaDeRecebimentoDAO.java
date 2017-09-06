package br.com.onesystem.dao;

import br.com.onesystem.domain.FormaDeRecebimento;

public class FormaDeRecebimentoDAO extends GenericDAO<FormaDeRecebimento> {

    public FormaDeRecebimentoDAO() {
        super(FormaDeRecebimento.class);
        limpar();
    }

    public FormaDeRecebimentoDAO porId(Long id) {
        where += " and formaDeRecebimento.id = :fId ";
        parametros.put("fId", id);
        return this;
    }

    public FormaDeRecebimentoDAO ativas() {
        where += " and formaDeRecebimento.ativo = :pAtiva ";
        parametros.put("pAtiva", true);
        return this;
    }

}
