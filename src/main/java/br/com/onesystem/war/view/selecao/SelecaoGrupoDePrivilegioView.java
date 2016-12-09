package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.war.service.GrupoPrivilegioService;
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
public class SelecaoGrupoDePrivilegioView implements Serializable {

    private GrupoDePrivilegio grupoPrivilegioSelecionado;
    private List<GrupoDePrivilegio> grupoPrivilegioLista;
    private List<GrupoDePrivilegio> grupoPrivilegiosFiltradas;

    @ManagedProperty("#{grupoPrivilegioService}")
    private GrupoPrivilegioService service;

    @PostConstruct
    public void init() {
        grupoPrivilegioLista = service.buscarGrupoDePrivilegio();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 750);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoGrupoPrivilegio", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(grupoPrivilegioSelecionado);
    }

    public GrupoDePrivilegio getGrupoDePrivilegioSelecionado() {
        return grupoPrivilegioSelecionado;
    }

    public void setGrupoDePrivilegioSelecionado(GrupoDePrivilegio grupoPrivilegioSelecionado) {
        this.grupoPrivilegioSelecionado = grupoPrivilegioSelecionado;
    }

    public List<GrupoDePrivilegio> getGrupoDePrivilegioLista() {
        return grupoPrivilegioLista;
    }

    public void setGrupoDePrivilegioLista(List<GrupoDePrivilegio> grupoPrivilegioLista) {
        this.grupoPrivilegioLista = grupoPrivilegioLista;
    }

    public List<GrupoDePrivilegio> getGrupoPrivilegiosFiltradas() {
        return grupoPrivilegiosFiltradas;
    }

    public void setGrupoPrivilegiosFiltradas(List<GrupoDePrivilegio> grupoPrivilegiosFiltradas) {
        this.grupoPrivilegiosFiltradas = grupoPrivilegiosFiltradas;
    }

    public GrupoPrivilegioService getService() {
        return service;
    }

    public void setService(GrupoPrivilegioService service) {
        this.service = service;
    }
}
