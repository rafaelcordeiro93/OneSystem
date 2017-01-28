package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.war.service.GrupoFiscalService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoGrupoFiscalView extends BasicCrudMBImpl<GrupoFiscal> implements Serializable {

    @ManagedProperty("#{grupoFiscalService}")
    private GrupoFiscalService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupoFiscais();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoGrupoFiscal");
    }

    public GrupoFiscalService getService() {
        return service;
    }

    public void setService(GrupoFiscalService service) {
        this.service = service;
    }
}
