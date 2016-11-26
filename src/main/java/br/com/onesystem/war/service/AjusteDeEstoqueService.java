package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "ajusteDeEstoqueService")
@ApplicationScoped
public class AjusteDeEstoqueService implements Serializable {

    public List<AjusteDeEstoque> buscarAjusteDeEstoques() {
        return new ArmazemDeRegistros<AjusteDeEstoque>(AjusteDeEstoque.class).listaTodosOsRegistros();
    }

}
