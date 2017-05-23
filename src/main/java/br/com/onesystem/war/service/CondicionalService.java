package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.CondicionalDAO;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import java.io.Serializable;
import java.util.List;

public class CondicionalService implements Serializable {

    public List<Condicional> buscarCondicionals() {
        return new ArmazemDeRegistros<>(Condicional.class).listaTodosOsRegistros();
    }

    public List<Condicional> buscarCondicionaisNo(EstadoDeCondicional estadoDeCondicional) {
        return new CondicionalDAO().porEstado(estadoDeCondicional).listaDeResultados();
    }

}
