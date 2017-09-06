package br.com.onesystem.war.service;

import br.com.onesystem.dao.CobrancaDAO;
import br.com.onesystem.domain.CobrancaVariavel;
import java.io.Serializable;
import java.util.List;

public class CobrancaService implements Serializable {

    public List<CobrancaVariavel> buscarCobrancas() {
        return new CobrancaDAO().listaDeResultados();
    }

}
