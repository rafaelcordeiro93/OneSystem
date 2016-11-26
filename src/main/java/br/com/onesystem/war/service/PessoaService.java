package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Pessoa;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "pessoaService")
@ApplicationScoped
public class PessoaService implements Serializable {

    public List<Pessoa> buscarPessoas() {
        return new ArmazemDeRegistros<Pessoa>(Pessoa.class).listaTodosOsRegistros();
    }

}
