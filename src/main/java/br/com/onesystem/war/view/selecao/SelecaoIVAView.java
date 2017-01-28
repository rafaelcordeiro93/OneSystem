package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.IVA;
import br.com.onesystem.war.service.IVAService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoIVAView extends BasicCrudMBImpl<IVA> implements Serializable {

    @ManagedProperty("#{ivaService}")
    private IVAService service;

    @PostConstruct
    public void init() {
        beans = service.buscarIVAs();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoIVA");
    }

    public IVAService getService() {
        return service;
    }

    public void setService(IVAService service) {
        this.service = service;
    }
}
