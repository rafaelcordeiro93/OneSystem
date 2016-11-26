package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.TransferenciaDespesaProvisionada;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "transferenciaDespesaProvisionadaService")
@ApplicationScoped
public class TransferenciaDespesaProvisionadaService implements Serializable {

    public List<TransferenciaDespesaProvisionada> buscarTransferenciaDespesaProvisionadas() {
        return new ArmazemDeRegistros<TransferenciaDespesaProvisionada>(TransferenciaDespesaProvisionada.class).listaTodosOsRegistros();
    }

}
