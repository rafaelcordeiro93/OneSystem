package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
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
public class SelecaoUnidadeMedidaItemView implements Serializable {

    private UnidadeMedidaItem unidadeMedidaItemSelecionada;
    private List<UnidadeMedidaItem> unidadeMedidaItemLista;
    private List<UnidadeMedidaItem> unidadeMedidaItemsFiltradas;

    @ManagedProperty("#{unidadeMedidaItemService}")
    private UnidadeMedidaItemService service;

    @PostConstruct
    public void init() {
        unidadeMedidaItemLista = service.buscarUnidadeMedidaItens();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoUnidadeMedidaItem", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(unidadeMedidaItemSelecionada);
    }

    public UnidadeMedidaItem getUnidadeMedidaItemSelecionada() {
        return unidadeMedidaItemSelecionada;
    }

    public void setUnidadeMedidaItemSelecionada(UnidadeMedidaItem unidadeMedidaItemSelecionada) {
        this.unidadeMedidaItemSelecionada = unidadeMedidaItemSelecionada;
    }

    public List<UnidadeMedidaItem> getUnidadeMedidaItemLista() {
        return unidadeMedidaItemLista;
    }

    public void setUnidadeMedidaItemLista(List<UnidadeMedidaItem> unidadeMedidaItemLista) {
        this.unidadeMedidaItemLista = unidadeMedidaItemLista;
    }

    public List<UnidadeMedidaItem> getUnidadeMedidaItemsFiltradas() {
        return unidadeMedidaItemsFiltradas;
    }

    public void setUnidadeMedidaItemsFiltradas(List<UnidadeMedidaItem> unidadeMedidaItemsFiltradas) {
        this.unidadeMedidaItemsFiltradas = unidadeMedidaItemsFiltradas;
    }

    public UnidadeMedidaItemService getService() {
        return service;
    }

    public void setService(UnidadeMedidaItemService service) {
        this.service = service;
    }
}
