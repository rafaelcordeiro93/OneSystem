package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ListaDePreco;
import java.io.Serializable;
import java.util.List;

public class ListaDePrecoService implements Serializable {

    public List<ListaDePreco> buscarListaPrecos() {
        return new ArmazemDeRegistros<ListaDePreco>(ListaDePreco.class).listaTodosOsRegistros();
    }

}
