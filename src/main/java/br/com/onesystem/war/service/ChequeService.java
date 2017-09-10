package br.com.onesystem.war.service;

import br.com.onesystem.dao.ChequeDAO;
import br.com.onesystem.domain.Cheque;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ChequeService implements Serializable {

    @Inject
    private ChequeDAO dao;
    
    public List<Cheque> buscarCheques() {
        return dao.listaDeResultados();
    }

}
