package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.war.service.GrupoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoGrupoView extends BasicCrudMBImpl<Grupo> implements Serializable {

    @ManagedProperty("#{grupoService}")
    private GrupoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoGrupo");
    }

    public GrupoService getService() {
        return service;
    }

    public void setService(GrupoService service) {
        this.service = service;
    }
}
