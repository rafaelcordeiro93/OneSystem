package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ListaDePreco;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "listaDePrecoService")
@ApplicationScoped
public class ListaDePrecoService implements Serializable {

    public List<ListaDePreco> buscarListaPrecos() {
        return new ArmazemDeRegistros<ListaDePreco>(ListaDePreco.class).listaTodosOsRegistros();
    }

}
