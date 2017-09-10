package br.com.onesystem.war.service;

import br.com.onesystem.dao.LancamentoBancarioDAO;
import br.com.onesystem.domain.LancamentoBancario;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class LancamentoBancarioService implements Serializable {

    @Inject
    private LancamentoBancarioDAO dao;
    
    public List<LancamentoBancario> buscarLancamentoBancarios() {
        return dao.listaDeResultados();
    }
}
