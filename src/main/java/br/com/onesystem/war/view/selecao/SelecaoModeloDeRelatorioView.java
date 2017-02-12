package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.war.service.ModeloDeRelatorioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoModeloDeRelatorioView extends BasicCrudMBImpl<ModeloDeRelatorio> implements Serializable {

    @ManagedProperty("#{modeloDeRelatorioService}")
    private ModeloDeRelatorioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarModeloDeRelatorio();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoModeloDeRelatorio");
    }

    public ModeloDeRelatorioService getService() {
        return service;
    }

    public void setService(ModeloDeRelatorioService service) {
        this.service = service;
    }
}
