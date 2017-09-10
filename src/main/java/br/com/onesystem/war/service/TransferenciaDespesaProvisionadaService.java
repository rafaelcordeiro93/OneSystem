package br.com.onesystem.war.service;

import br.com.onesystem.dao.TransferenciaDespesaProvisionadaDAO;
import br.com.onesystem.domain.TransferenciaDespesaProvisionada;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class TransferenciaDespesaProvisionadaService implements Serializable {

    @Inject
    private TransferenciaDespesaProvisionadaDAO dao;
    
    public List<TransferenciaDespesaProvisionada> buscarTransferenciaDespesaProvisionadas() {
        return dao.listaDeResultados();
    }

}
