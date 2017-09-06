package br.com.onesystem.war.service;

import br.com.onesystem.dao.FaturaEmitidaDAO;
import br.com.onesystem.domain.FaturaEmitida;
import java.io.Serializable;
import java.util.List;

public class FaturaEmitidaService implements Serializable {

    public List<FaturaEmitida> buscarFaturasEmitidas() {
        return new FaturaEmitidaDAO().listaDeResultados();
    }

}
