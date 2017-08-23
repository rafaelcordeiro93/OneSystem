package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.TransferenciaDespesaProvisionada;
import java.io.Serializable;
import java.util.List;

public class TransferenciaDespesaProvisionadaService implements Serializable {

    public List<TransferenciaDespesaProvisionada> buscarTransferenciaDespesaProvisionadas() {
        return new ArmazemDeRegistros<TransferenciaDespesaProvisionada>(TransferenciaDespesaProvisionada.class).listaTodosOsRegistros();
    }

}
