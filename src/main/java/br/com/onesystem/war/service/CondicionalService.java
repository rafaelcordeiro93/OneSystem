package br.com.onesystem.war.service;

import br.com.onesystem.dao.CondicionalDAO;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class CondicionalService implements Serializable {

    @Inject
    private CondicionalDAO dao;
    
    public List<Condicional> buscarCondicionals() {
        return dao.listaDeResultados();
    }

    public List<Condicional> buscarCondicionaisNo(EstadoDeCondicional estadoDeCondicional) {
        return dao.porEstado(estadoDeCondicional).listaDeResultados();
    }

}
