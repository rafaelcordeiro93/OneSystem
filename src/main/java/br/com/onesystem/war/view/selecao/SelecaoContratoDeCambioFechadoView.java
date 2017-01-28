package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.war.service.ContratoDeCambioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoContratoDeCambioFechadoView extends BasicCrudMBImpl<ContratoDeCambio> implements Serializable {

    @ManagedProperty("#{contratoDeCambioService}")
    private ContratoDeCambioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarContratosFechadosParaCambio();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoContratoDeCambioFechado");
    }

    public ContratoDeCambioService getService() {
        return service;
    }

    public void setService(ContratoDeCambioService service) {
        this.service = service;
    }
}
