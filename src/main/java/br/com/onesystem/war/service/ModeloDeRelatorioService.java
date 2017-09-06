package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.ModeloDeRelatorioDAO;
import br.com.onesystem.domain.ModeloDeRelatorio;
import java.io.Serializable;
import java.util.List;

public class ModeloDeRelatorioService implements Serializable {

    public List<ModeloDeRelatorio> buscarModeloDeRelatorio() {
        return new ModeloDeRelatorioDAO().listaDeResultados();
    }

}
