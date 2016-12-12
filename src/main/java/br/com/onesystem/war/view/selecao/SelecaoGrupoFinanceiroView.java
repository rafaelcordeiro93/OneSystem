package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.war.service.GrupoFinanceiroService;
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
public class SelecaoGrupoFinanceiroView implements Serializable {

    private GrupoFinanceiro grupoFinanceiroSelecionado;
    private List<GrupoFinanceiro> grupoFinanceiroLista;
    private List<GrupoFinanceiro> grupoFinanceirosFiltradas;

    @ManagedProperty("#{grupoFinanceiroService}")
    private GrupoFinanceiroService service;

    @PostConstruct
    public void init() {
        grupoFinanceiroLista = service.buscarGruposFinanceiros();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoGrupoFinanceiro", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(grupoFinanceiroSelecionado);
    }

    public GrupoFinanceiro getGrupoFinanceiroSelecionado() {
        return grupoFinanceiroSelecionado;
    }

    public void setGrupoFinanceiroSelecionado(GrupoFinanceiro grupoFinanceiroSelecionado) {
        this.grupoFinanceiroSelecionado = grupoFinanceiroSelecionado;
    }

    public List<GrupoFinanceiro> getGrupoFinanceiroLista() {
        return grupoFinanceiroLista;
    }

    public void setGrupoFinanceiroLista(List<GrupoFinanceiro> grupoFinanceiroLista) {
        this.grupoFinanceiroLista = grupoFinanceiroLista;
    }

    public List<GrupoFinanceiro> getGrupoFinanceirosFiltradas() {
        return grupoFinanceirosFiltradas;
    }

    public void setGrupoFinanceirosFiltradas(List<GrupoFinanceiro> grupoFinanceirosFiltradas) {
        this.grupoFinanceirosFiltradas = grupoFinanceirosFiltradas;
    }

    public GrupoFinanceiroService getService() {
        return service;
    }

    public void setService(GrupoFinanceiroService service) {
        this.service = service;
    }
}
