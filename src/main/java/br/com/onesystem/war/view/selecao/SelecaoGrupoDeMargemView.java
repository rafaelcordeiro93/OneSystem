package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.war.service.GrupoDeMargemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoGrupoDeMargemView extends BasicCrudMBImpl<Margem> implements Serializable {

    @ManagedProperty("#{grupoDeMargemService}")
    private GrupoDeMargemService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGrupoDeMargens();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoGrupoDeMargem");
    }

    public GrupoDeMargemService getService() {
        return service;
    }

    public void setService(GrupoDeMargemService service) {
        this.service = service;
    }
}
