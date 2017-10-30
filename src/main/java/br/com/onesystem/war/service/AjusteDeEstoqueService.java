package br.com.onesystem.war.service;

import br.com.onesystem.dao.AjusteDeEstoqueDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.AjusteDeEstoque;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AjusteDeEstoqueService implements Serializable {

    @Inject
    private ArmazemDeRegistros<AjusteDeEstoque> armazem;

    @Inject
    private AjusteDeEstoqueDAO dao;

    public List<AjusteDeEstoque> buscarAjusteDeEstoques() {
        return armazem.daClasse(AjusteDeEstoque.class).listaTodosOsRegistros();
    }

}
