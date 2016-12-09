package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.war.service.GrupoService;
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
public class SelecaoGrupoView implements Serializable {

    private Grupo grupoSelecionada;
    private List<Grupo> grupoLista;
    private List<Grupo> gruposFiltrados;

    @ManagedProperty("#{grupoService}")
    private GrupoService service;

    @PostConstruct
    public void init() {
        grupoLista = service.buscarGrupos();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoGrupo", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(grupoSelecionada);
    }

    public Grupo getGrupoSelecionada() {
        return grupoSelecionada;
    }

    public void setGrupoSelecionada(Grupo grupoSelecionada) {
        this.grupoSelecionada = grupoSelecionada;
    }

    public List<Grupo> getGrupoLista() {
        return grupoLista;
    }

    public void setGrupoLista(List<Grupo> grupoLista) {
        this.grupoLista = grupoLista;
    }

    public List<Grupo> getGruposFiltrados() {
        return gruposFiltrados;
    }

    public void setGruposFiltrados(List<Grupo> gruposFiltrados) {
        this.gruposFiltrados = gruposFiltrados;
    }

    public GrupoService getService() {
        return service;
    }

    public void setService(GrupoService service) {
        this.service = service;
    }
}
