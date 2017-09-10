package br.com.onesystem.war.service;

import br.com.onesystem.dao.SaqueBancarioDAO;
import br.com.onesystem.domain.SaqueBancario;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class SaqueBancarioService implements Serializable {

    @Inject
    private SaqueBancarioDAO dao;
    
    public List<SaqueBancario> buscarSaqueBancarios() {
        return dao.listaDeResultados();
    }
}
