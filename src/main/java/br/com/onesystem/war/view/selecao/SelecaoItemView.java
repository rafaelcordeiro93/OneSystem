package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoItemView extends BasicCrudMBImpl<Item> implements Serializable {

    @ManagedProperty("#{itemService}")
    private ItemService service;

    @PostConstruct
    public void init() {
        beans = service.buscarItems();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoItem");
    }

    public ItemService getService() {
        return service;
    }

    public void setService(ItemService service) {
        this.service = service;
    }
}
