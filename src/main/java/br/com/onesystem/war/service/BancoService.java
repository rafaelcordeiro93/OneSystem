package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Banco;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "bancoService")
@ApplicationScoped
public class BancoService implements Serializable {
    
    public List<Banco> buscarBancos(){
        return new ArmazemDeRegistros<Banco>(Banco.class).listaTodosOsRegistros();
    }
    
}
