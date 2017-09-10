package br.com.onesystem.war.service;

import br.com.onesystem.dao.ListaDePrecoDAO;
import br.com.onesystem.domain.ListaDePreco;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ListaDePrecoService implements Serializable {

    @Inject
    private ListaDePrecoDAO dao;
    
    public List<ListaDePreco> buscarListaPrecos() {
        return dao.listaDeResultados();
    }

}
