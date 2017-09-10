package br.com.onesystem.war.service;

import br.com.onesystem.dao.ContaDeEstoqueDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ContaDeEstoqueService implements Serializable {

    @Inject
    private ContaDeEstoqueDAO dao;
    
    public List<ContaDeEstoque> buscarContaDeEstoque() {
         return dao.listaDeResultados();
    }

}
