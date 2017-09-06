package br.com.onesystem.war.service;

import br.com.onesystem.dao.ComissaoDAO;
import br.com.onesystem.domain.Comissao;
import java.io.Serializable;
import java.util.List;

public class ComissaoService implements Serializable {

    public List<Comissao> buscarComissao() {
        return new ComissaoDAO().listaDeResultados();
    }

}
