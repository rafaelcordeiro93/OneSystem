package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoDeMargem;
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

    private GrupoDeMargem grupoDeMargemSelecionada;
    private List<GrupoDeMargem> grupoDeMargemLista;
    private List<GrupoDeMargem> grupoDeMargemFiltradas;

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
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoGrupoDeMargem", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(grupoDeMargemSelecionada);
    }

    public GrupoDeMargem getGrupoDeMargemSelecionada() {
        return grupoDeMargemSelecionada;
    }

    public void setGrupoDeMargemSelecionada(GrupoDeMargem grupoDeMargemSelecionada) {
        this.grupoDeMargemSelecionada = grupoDeMargemSelecionada;
    }

    public List<GrupoDeMargem> getGrupoDeMargemLista() {
        return grupoDeMargemLista;
    }

    public void setGrupoDeMargemLista(List<GrupoDeMargem> grupoDeMargemLista) {
        this.grupoDeMargemLista = grupoDeMargemLista;
    }

    public List<GrupoDeMargem> getGrupoDeMargemFiltradas() {
        return grupoDeMargemFiltradas;
    }

    public void setGrupoDeMargemsFiltradas(List<GrupoDeMargem> grupoDeMargemsFiltradas) {
        this.grupoDeMargemFiltradas = grupoDeMargemsFiltradas;
    }

    public GrupoDeMargemService getService() {
        return service;
    }

    public void setService(GrupoDeMargemService service) {
        this.service = service;
    }
}
