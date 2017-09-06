package br.com.onesystem.dao;

import br.com.onesystem.domain.Deposito;

public class DepositoDAO extends GenericDAO<Deposito> {

    public DepositoDAO() {
        super(Deposito.class);
        limpar();
    }

    public DepositoDAO porId(Long id) {
        where += " and deposito.id = :dId ";
        parametros.put("dId", id);
        return this;
    }

    public DepositoDAO porNome(Deposito deposito) {
        where += " and deposito.nome = :dNome ";
        parametros.put("dNome", deposito.getNome());
        return this;
    }

}
