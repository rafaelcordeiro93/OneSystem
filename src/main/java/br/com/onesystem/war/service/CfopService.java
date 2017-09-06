package br.com.onesystem.war.service;

import br.com.onesystem.dao.CfopDAO;
import br.com.onesystem.domain.Cfop;
import java.io.Serializable;
import java.util.List;

public class CfopService implements Serializable {

    public List<Cfop> buscarCfops() {
        return new CfopDAO().listaDeResultados();
    }

}
