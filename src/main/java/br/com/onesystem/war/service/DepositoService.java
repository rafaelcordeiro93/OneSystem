package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Deposito;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "depositoService")
@ApplicationScoped
public class DepositoService implements Serializable {

    public List<Deposito> buscarDepositos() {
        return new ArmazemDeRegistros<Deposito>(Deposito.class).listaTodosOsRegistros();
    }

}
