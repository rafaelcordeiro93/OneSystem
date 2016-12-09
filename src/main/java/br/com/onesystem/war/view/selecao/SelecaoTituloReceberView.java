package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Titulo;
import br.com.onesystem.war.service.TituloService;
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
public class SelecaoTituloReceberView implements Serializable {

    private Titulo tituloSelecionada;
    private List<Titulo> tituloLista;
    private List<Titulo> titulosFiltradas;

    @ManagedProperty("#{tituloService}")
    private TituloService service;

    @PostConstruct
    public void init() {
        tituloLista = service.buscarTitulosAReceber();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoTituloReceber", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(tituloSelecionada);
    }

    public Titulo getTituloSelecionada() {
        return tituloSelecionada;
    }

    public void setTituloSelecionada(Titulo tituloSelecionada) {
        this.tituloSelecionada = tituloSelecionada;
    }

    public List<Titulo> getTituloLista() {
        return tituloLista;
    }

    public void setTituloLista(List<Titulo> tituloLista) {
        this.tituloLista = tituloLista;
    }

    public List<Titulo> getTitulosFiltradas() {
        return titulosFiltradas;
    }

    public void setTitulosFiltradas(List<Titulo> titulosFiltradas) {
        this.titulosFiltradas = titulosFiltradas;
    }

    public TituloService getService() {
        return service;
    }

    public void setService(TituloService service) {
        this.service = service;
    }
}
