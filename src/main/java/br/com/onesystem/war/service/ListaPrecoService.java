package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ListaPreco;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "listaPrecoService")
@ApplicationScoped
public class ListaPrecoService implements Serializable {

    public List<ListaPreco> buscarListaPrecos() {
        return new ArmazemDeRegistros<ListaPreco>(ListaPreco.class).listaTodosOsRegistros();
    }

}
