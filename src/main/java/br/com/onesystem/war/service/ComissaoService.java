package br.com.onesystem.war.service;

import br.com.onesystem.dao.ComissaoDAO;
import br.com.onesystem.domain.Comissao;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ComissaoService implements Serializable {

    @Inject
    private ComissaoDAO dao;
    
    public List<Comissao> buscarComissao() {
        return dao.listaDeResultados();
    }

}
