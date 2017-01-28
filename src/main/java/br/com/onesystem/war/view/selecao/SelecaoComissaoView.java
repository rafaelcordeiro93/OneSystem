package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.war.service.ComissaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoComissaoView extends BasicCrudMBImpl<Comissao> implements Serializable {

    @ManagedProperty("#{comissaoService}")
    private ComissaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarComissao();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoComissao");
    }

    public ComissaoService getService() {
        return service;
    }

    public void setService(ComissaoService service) {
        this.service = service;
    }
}
