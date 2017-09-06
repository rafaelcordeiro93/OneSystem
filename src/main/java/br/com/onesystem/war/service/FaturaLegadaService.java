package br.com.onesystem.war.service;

import br.com.onesystem.dao.FaturaLegadaDAO;
import br.com.onesystem.domain.FaturaLegada;
import java.io.Serializable;
import java.util.List;

public class FaturaLegadaService implements Serializable {

    public List<FaturaLegada> buscarFaturasLegadas() {
        return new FaturaLegadaDAO().listaDeResultados();
    }

}
