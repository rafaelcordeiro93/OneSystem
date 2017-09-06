package br.com.onesystem.war.service;

import br.com.onesystem.dao.MoedaDAO;
import br.com.onesystem.domain.Moeda;
import java.io.Serializable;
import java.util.List;

public class MoedaService implements Serializable {

    public List<Moeda> buscarMoedas() {
        return new MoedaDAO().listaDeResultados();
    }

}
