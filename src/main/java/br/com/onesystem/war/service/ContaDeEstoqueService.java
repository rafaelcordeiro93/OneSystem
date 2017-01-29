package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ContaDeEstoque;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "comissaoService")
@ApplicationScoped
public class ContaDeEstoqueService implements Serializable {

    public List<ContaDeEstoque> buscarContaDeEstoque() {
        return new ArmazemDeRegistros<ContaDeEstoque>(ContaDeEstoque.class).listaTodosOsRegistros();
    }

}
