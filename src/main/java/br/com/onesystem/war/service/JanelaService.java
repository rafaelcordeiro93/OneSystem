package br.com.onesystem.war.service;

import br.com.onesystem.dao.JanelaDAO;
import br.com.onesystem.domain.Janela;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class JanelaService implements Serializable {

    @Inject
    private JanelaDAO dao;
    
    public List<Janela> buscarJanelas() {
        return dao.listaDeResultados();
    }

}
