package br.com.onesystem.war.view;

import br.com.onesystem.domain.ContratoDeCambio;
import br.com.onesystem.war.service.ContratoDeCambioService;
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
public class SelecaoContratoDeCambioView implements Serializable {

    private ContratoDeCambio contratoDeCambioSelecionado;
    private List<ContratoDeCambio> contratoDeCambioLista;
    private List<ContratoDeCambio> contratoDeCambioFiltrados;

    @ManagedProperty("#{contratoDeCambioService}")
    private ContratoDeCambioService service;

    @PostConstruct
    public void init() {
        contratoDeCambioLista = service.buscarContratosDeCambio();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoContratoDeCambio", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(contratoDeCambioSelecionado);
    }

    public ContratoDeCambio getContratoDeCambioSelecionado() {
        return contratoDeCambioSelecionado;
    }

    public void setContratoDeCambioSelecionado(ContratoDeCambio contratoDeCambioSelecionado) {
        this.contratoDeCambioSelecionado = contratoDeCambioSelecionado;
    }

    public List<ContratoDeCambio> getContratoDeCambioLista() {
        return contratoDeCambioLista;
    }

    public void setContratoDeCambioLista(List<ContratoDeCambio> contratoDeCambioLista) {
        this.contratoDeCambioLista = contratoDeCambioLista;
    }

    public List<ContratoDeCambio> getContratoDeCambioFiltrados() {
        return contratoDeCambioFiltrados;
    }

    public void setContratoDeCambioFiltrados(List<ContratoDeCambio> contratoDeCambioFiltrados) {
        this.contratoDeCambioFiltrados = contratoDeCambioFiltrados;
    }

    public ContratoDeCambioService getService() {
        return service;
    }

    public void setService(ContratoDeCambioService service) {
        this.service = service;
    }
}
