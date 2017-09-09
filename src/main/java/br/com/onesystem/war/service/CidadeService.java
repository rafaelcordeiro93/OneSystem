package br.com.onesystem.war.service;

import br.com.onesystem.dao.CidadeDAO;
import br.com.onesystem.domain.Cidade;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class CidadeService implements Serializable {

    @Inject
    private CidadeDAO dao;

    public List<Cidade> buscarCidades() {
        return dao.listaDeResultados();
    }

}
