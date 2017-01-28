package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.war.service.GrupoPrivilegioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoGrupoDePrivilegioView extends BasicCrudMBImpl<GrupoDePrivilegio> implements Serializable {

    @ManagedProperty("#{grupoPrivilegioService}")
    private GrupoPrivilegioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupoDePrivilegio();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoGrupoPrivilegio");
    }

    public GrupoPrivilegioService getService() {
        return service;
    }

    public void setService(GrupoPrivilegioService service) {
        this.service = service;
    }
}
