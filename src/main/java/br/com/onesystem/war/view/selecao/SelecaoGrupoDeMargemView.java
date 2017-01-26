package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.war.service.GrupoDeMargemService;
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
public class SelecaoGrupoDeMargemView implements Serializable {

    private Margem grupoDeMargemSelecionada;
    private List<Margem> grupoDeMargemLista;
    private List<Margem> grupoDeMargemsFiltradas;

    @ManagedProperty("#{grupoDeMargemService}")
    private GrupoDeMargemService service;

    @PostConstruct
    public void init() {
        grupoDeMargemLista = service.buscarGrupoDeMargens();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("width", 950);
        opcoes.put("draggable", false);
        opcoes.put("height", 500);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%"); 
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoGrupoDeMargem", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(grupoDeMargemSelecionada);
    }

    public Margem getGrupoDeMargemSelecionada() {
        return grupoDeMargemSelecionada;
    }

    public void setGrupoDeMargemSelecionada(Margem grupoDeMargemSelecionada) {
        this.grupoDeMargemSelecionada = grupoDeMargemSelecionada;
    }

    public List<Margem> getGrupoDeMargemLista() {
        return grupoDeMargemLista;
    }

    public void setGrupoDeMargemLista(List<Margem> grupoDeMargemLista) {
        this.grupoDeMargemLista = grupoDeMargemLista;
    }

    public List<Margem> getGrupoDeMargemsFiltradas() {
        return grupoDeMargemsFiltradas;
    }

    public void setGrupoDeMargemsFiltradas(List<Margem> grupoDeMargemsFiltradas) {
        this.grupoDeMargemsFiltradas = grupoDeMargemsFiltradas;
    }

    public GrupoDeMargemService getService() {
        return service;
    }

    public void setService(GrupoDeMargemService service) {
        this.service = service;
    }
}
