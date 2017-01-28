package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.war.service.FilialService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoFilialView extends BasicCrudMBImpl<Filial> implements Serializable {

    @ManagedProperty("#{filialService}")
    private FilialService service;

    @PostConstruct
    public void init() {
        beans = service.buscarFilials();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoFilial");
    }

    public FilialService getService() {
        return service;
    }

    public void setService(FilialService service) {
        this.service = service;
    }
}
