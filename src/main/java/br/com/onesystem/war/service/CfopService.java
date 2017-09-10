package br.com.onesystem.war.service;

import br.com.onesystem.dao.CfopDAO;
import br.com.onesystem.domain.Cfop;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class CfopService implements Serializable {

    @Inject
    private CfopDAO dao;
    public List<Cfop> buscarCfops() {
        return dao.listaDeResultados();
    }

}
