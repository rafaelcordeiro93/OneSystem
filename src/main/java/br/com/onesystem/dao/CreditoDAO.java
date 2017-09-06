package br.com.onesystem.dao;

import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.Pessoa;

public class CreditoDAO extends GenericDAO<Credito> {

    public CreditoDAO() {
        super(Credito.class);
        limpar();
    }

    public CreditoDAO porPessoa(Pessoa pessoa) {
        where += " and credito.pessoa = :pPessoa";
        parametros.put("pPessoa", pessoa);
        return this;
    }

}
