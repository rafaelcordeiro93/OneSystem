package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import java.io.Serializable;
import java.util.List;

public class AjusteDeEstoqueService implements Serializable {

    public List<AjusteDeEstoque> buscarAjusteDeEstoques() {
        return new ArmazemDeRegistros<AjusteDeEstoque>(AjusteDeEstoque.class).listaTodosOsRegistros();
    }
  
}
