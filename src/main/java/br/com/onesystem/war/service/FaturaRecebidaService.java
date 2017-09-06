package br.com.onesystem.war.service;

import br.com.onesystem.dao.FaturaRecebidaDAO;
import br.com.onesystem.domain.FaturaRecebida;
import java.io.Serializable;
import java.util.List;

public class FaturaRecebidaService implements Serializable {

    public List<FaturaRecebida> buscarFaturasEmitidas() {
        return new FaturaRecebidaDAO().listaDeResultados();
    }

}
