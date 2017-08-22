package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.PessoaDAO;
import br.com.onesystem.domain.Pessoa;
import java.io.Serializable;
import java.util.List;

public class PessoaService implements Serializable {

    public List<Pessoa> buscarPessoas() {
        return new ArmazemDeRegistros<Pessoa>(Pessoa.class).listaTodosOsRegistros();
    }
  
    public List<Pessoa> buscarFornecedores(){
        return new PessoaDAO().porFornecedor().listaDeResultados();
    }

}
