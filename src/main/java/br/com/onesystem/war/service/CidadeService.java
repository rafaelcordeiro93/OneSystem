package br.com.onesystem.war.service;

import br.com.onesystem.dao.CidadeDAO;
import br.com.onesystem.domain.Cidade;
import java.io.Serializable;
import java.util.List;

public class CidadeService implements Serializable {

    public List<Cidade> buscarCidades() {
        return new CidadeDAO().listaDeResultados();
    }

}
