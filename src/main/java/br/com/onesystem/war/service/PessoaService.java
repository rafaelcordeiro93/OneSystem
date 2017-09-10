package br.com.onesystem.war.service;

import br.com.onesystem.dao.PessoaDAO;
import br.com.onesystem.domain.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class PessoaService implements Serializable {

    @Inject
    private PessoaDAO dao;
    
    public List<Pessoa> buscarPessoas() {
        return dao.listaDeResultados();
    }

    public List<Pessoa> buscarFornecedores() {
        return dao.porFornecedor().listaDeResultados();
    }

}
