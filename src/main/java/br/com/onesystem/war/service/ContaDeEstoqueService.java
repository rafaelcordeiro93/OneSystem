package br.com.onesystem.war.service;

import br.com.onesystem.dao.ContaDeEstoqueDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import java.io.Serializable;
import java.util.List;

public class ContaDeEstoqueService implements Serializable {

    public List<ContaDeEstoque> buscarContaDeEstoque() {
         return new ContaDeEstoqueDAO().listaDeResultados();
    }

}
