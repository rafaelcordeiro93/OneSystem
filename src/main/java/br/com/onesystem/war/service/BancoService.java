package br.com.onesystem.war.service;

import br.com.onesystem.dao.BancoDAO;
import br.com.onesystem.domain.Banco;
import java.io.Serializable;
import java.util.List;

public class BancoService implements Serializable {
    
    public List<Banco> buscarBancos(){
        return new BancoDAO().listaDeResultados();
    }
    
}
