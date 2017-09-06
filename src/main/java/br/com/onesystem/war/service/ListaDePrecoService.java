package br.com.onesystem.war.service;

import br.com.onesystem.dao.ListaDePrecoDAO;
import br.com.onesystem.domain.ListaDePreco;
import java.io.Serializable;
import java.util.List;

public class ListaDePrecoService implements Serializable {

    public List<ListaDePreco> buscarListaPrecos() {
        return new ListaDePrecoDAO().listaDeResultados();
    }

}
