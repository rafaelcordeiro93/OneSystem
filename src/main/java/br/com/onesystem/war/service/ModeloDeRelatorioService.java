package br.com.onesystem.war.service;

import br.com.onesystem.dao.ModeloDeRelatorioDAO;
import br.com.onesystem.domain.ModeloDeRelatorio;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class ModeloDeRelatorioService implements Serializable {

    @Inject
    private ModeloDeRelatorioDAO dao;
    
    public List<ModeloDeRelatorio> buscarModeloDeRelatorio() {
        return dao.listaDeResultados();
    }

}
