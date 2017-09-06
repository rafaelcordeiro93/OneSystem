package br.com.onesystem.war.service;

import br.com.onesystem.dao.ChequeDAO;
import br.com.onesystem.domain.Cheque;
import java.io.Serializable;
import java.util.List;

public class ChequeService implements Serializable {

    public List<Cheque> buscarCheques() {
        return new ChequeDAO().listaDeResultados();
    }

}
