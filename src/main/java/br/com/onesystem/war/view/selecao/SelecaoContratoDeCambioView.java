package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.war.service.ContratoDeCambioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SelecaoContratoDeCambioView extends BasicCrudMBImpl<ContratoDeCambio> implements Serializable {

    @ManagedProperty("#{contratoDeCambioService}")
    private ContratoDeCambioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarContratosDeCambio();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoContratoDeCambio");
    }

    public ContratoDeCambioService getService() {
        return service;
    }

    public void setService(ContratoDeCambioService service) {
        this.service = service;
    }
}
