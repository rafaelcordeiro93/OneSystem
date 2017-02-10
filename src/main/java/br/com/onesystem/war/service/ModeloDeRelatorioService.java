package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ModeloDeRelatorio;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "modeloDeRelatorioService")
@ApplicationScoped
public class ModeloDeRelatorioService implements Serializable {

    public List<ModeloDeRelatorio> buscarModeloDeRelatorio() {
        return new ArmazemDeRegistros<ModeloDeRelatorio>(ModeloDeRelatorio.class).listaTodosOsRegistros();
    }

}
