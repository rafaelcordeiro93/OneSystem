package br.com.onesystem.dao;

import br.com.onesystem.domain.Banco;

public class BancoDAO extends GenericDAO<Banco>{

    public BancoDAO(){
        super(Banco.class);
        limpar();
    }

    public BancoDAO porNome(Banco banco) {
        where += " and banco.nome = :bNome ";
        parametros.put("bNome", banco.getNome());
        return this;
    }

    public BancoDAO porId(Long id) {
        where += " and banco.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
