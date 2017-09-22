package br.com.onesystem.war.service;

import br.com.onesystem.dao.BancoDAO;
import br.com.onesystem.domain.Banco;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class BancoService implements Serializable {
    
    @Inject
    private BancoDAO dao;
    
    public List<Banco> buscarBancos(){
        return dao.listaDeResultados();
    }
    
}
