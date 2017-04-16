package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.Banco;
import java.io.Serializable;
import java.util.List;

public class BancoService implements Serializable {
    
    public List<Banco> buscarBancos(){
        return new ArmazemDeRegistros<Banco>(Banco.class).listaTodosOsRegistros();
    }
    
}
