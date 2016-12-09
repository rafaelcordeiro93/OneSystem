package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.war.service.GrupoFiscalService;
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
public class SelecaoGrupoFiscalView implements Serializable {

    private GrupoFiscal grupoFiscalSelecionada;
    private List<GrupoFiscal> grupoFiscalLista;
    private List<GrupoFiscal> grupoFiscalFiltradas;

    @ManagedProperty("#{grupoFiscalService}")
    private GrupoFiscalService service;

    @PostConstruct
    public void init() {
        grupoFiscalLista = service.buscarGrupoFiscais();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoGrupoFiscal", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(grupoFiscalSelecionada);
    }

    public GrupoFiscal getGrupoFiscalSelecionada() {
        return grupoFiscalSelecionada;
    }

    public void setGrupoFiscalSelecionada(GrupoFiscal grupoFiscalSelecionada) {
        this.grupoFiscalSelecionada = grupoFiscalSelecionada;
    }

    public List<GrupoFiscal> getGrupoFiscalLista() {
        return grupoFiscalLista;
    }

    public void setGrupoFiscalLista(List<GrupoFiscal> grupoFiscalLista) {
        this.grupoFiscalLista = grupoFiscalLista;
    }

    public List<GrupoFiscal> getGrupoFiscalFiltradas() {
        return grupoFiscalFiltradas;
    }

    public void setGrupoFiscalsFiltradas(List<GrupoFiscal> grupoFiscalsFiltradas) {
        this.grupoFiscalFiltradas = grupoFiscalsFiltradas;
    }

    public GrupoFiscalService getService() {
        return service;
    }

    public void setService(GrupoFiscalService service) {
        this.service = service;
    }
}
