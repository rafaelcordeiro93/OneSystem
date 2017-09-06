package br.com.onesystem.war.service;

import br.com.onesystem.dao.PessoaDAO;
import br.com.onesystem.domain.Pessoa;
import java.io.Serializable;
import java.util.List;

public class PessoaService implements Serializable {

    public List<Pessoa> buscarPessoas() {
        return new PessoaDAO().listaDeResultados();
    }

    public List<Pessoa> buscarFornecedores() {
        return new PessoaDAO().porFornecedor().listaDeResultados();
    }

}
