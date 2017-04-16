package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Cheque;
import java.io.Serializable;
import java.util.List;

public class ChequeService implements Serializable {

    public List<Cheque> buscarCheques() {
        return new ArmazemDeRegistros<Cheque>(Cheque.class).listaTodosOsRegistros();
    }
}
