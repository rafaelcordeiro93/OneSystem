package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.war.service.ListaDePrecoService;
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
public class SelecaoListaDePrecoView implements Serializable {

    private ListaDePreco listaDePrecoSelecionada;
    private List<ListaDePreco> listaDePrecoLista;
    private List<ListaDePreco> listaDePrecosFiltrados;

    @ManagedProperty("#{listaDePrecoService}")
    private ListaDePrecoService service;

    @PostConstruct
    public void init() {
        listaDePrecoLista = service.buscarListaPrecos();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoListaDePreco", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(listaDePrecoSelecionada);
    }

    public ListaDePreco getListaDePrecoSelecionada() {
        return listaDePrecoSelecionada;
    }

    public void setListaDePrecoSelecionada(ListaDePreco listaDePrecoSelecionada) {
        this.listaDePrecoSelecionada = listaDePrecoSelecionada;
    }

    public List<ListaDePreco> getListaDePrecoLista() {
        return listaDePrecoLista;
    }

    public void setListaDePrecoLista(List<ListaDePreco> listaDePrecoLista) {
        this.listaDePrecoLista = listaDePrecoLista;
    }

    public List<ListaDePreco> getListaDePrecosFiltrados() {
        return listaDePrecosFiltrados;
    }

    public void setListaDePrecosFiltrados(List<ListaDePreco> listaDePrecosFiltrados) {
        this.listaDePrecosFiltrados = listaDePrecosFiltrados;
    }

    public ListaDePrecoService getService() {
        return service;
    }

    public void setService(ListaDePrecoService service) {
        this.service = service;
    }
}
