package br.com.onesystem.dao;

import br.com.onesystem.domain.Pessoa;

public class PessoaDAO extends GenericDAO<Pessoa> {

    public PessoaDAO() {
        super(Pessoa.class);
    }

    public PessoaDAO porNome(Pessoa pessoa) {
        where += " and pessoa.nome = :pNome ";
        parametros.put("pNome", pessoa.getNome());
        return this;
    }

    public PessoaDAO porRuc(Pessoa pessoa) {
        where += " and pessoa.ruc = :pRuc ";
        parametros.put("pRuc", pessoa.getRuc());
        return this;
    }

    public PessoaDAO porId(Long id) {
        where += " and pessoa.id = :pId ";
        parametros.put("pId", id);
        return this;
    }
    
    public PessoaDAO porFornecedor() {
        where += " and pessoa.categoriaFornecedor = true ";       
        return this;
    }

}
