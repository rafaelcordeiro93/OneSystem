package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoUnidadeMedidaItemView extends BasicCrudMBImpl<UnidadeMedidaItem> implements Serializable {

    @ManagedProperty("#{unidadeMedidaItemService}")
    private UnidadeMedidaItemService service;

    @PostConstruct
    public void init() {
        beans = service.buscarUnidadeMedidaItens();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoUnidadeMedidaItem");
    }

    public UnidadeMedidaItemService getService() {
        return service;
    }

    public void setService(UnidadeMedidaItemService service) {
        this.service = service;
    }
}
