package br.com.onesystem.war.service;

import br.com.onesystem.dao.MoedaDAO;
import br.com.onesystem.domain.Moeda;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class MoedaService implements Serializable {

    @Inject
    private MoedaDAO dao;
    
    public List<Moeda> buscarMoedas() {
        return dao.listaDeResultados();
    }

}
