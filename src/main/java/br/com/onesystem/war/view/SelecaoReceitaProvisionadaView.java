package br.com.onesystem.war.view;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.war.service.ReceitaProvisionadaService;
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
public class SelecaoReceitaProvisionadaView implements Serializable {

    private ReceitaProvisionada receitaProvisionadaSelecionada;
    private List<ReceitaProvisionada> receitaProvisionadaLista;
    private List<ReceitaProvisionada> receitasProvisionadasFiltradas;

    @ManagedProperty("#{receitaProvisionadaService}")
    private ReceitaProvisionadaService service;

    @PostConstruct
    public void init() {
        receitaProvisionadaLista = service.buscarReceitaProvisionadasAReceber();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoReceitaProvisionada", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(receitaProvisionadaSelecionada);
    }

    public ReceitaProvisionada getReceitaProvisionadaSelecionada() {
        return receitaProvisionadaSelecionada;
    }

    public void setReceitaProvisionadaSelecionada(ReceitaProvisionada receitaProvisionadaSelecionada) {
        this.receitaProvisionadaSelecionada = receitaProvisionadaSelecionada;
    }

    public List<ReceitaProvisionada> getReceitaProvisionadaLista() {
        return receitaProvisionadaLista;
    }

    public void setReceitaProvisionadaLista(List<ReceitaProvisionada> receitaProvisionadaLista) {
        this.receitaProvisionadaLista = receitaProvisionadaLista;
    }

    public List<ReceitaProvisionada> getReceitasProvisionadasFiltradas() {
        return receitasProvisionadasFiltradas;
    }

    public void setReceitasProvisionadasFiltradas(List<ReceitaProvisionada> receitasProvisionadasFiltradas) {
        this.receitasProvisionadasFiltradas = receitasProvisionadasFiltradas;
    }

    public ReceitaProvisionadaService getService() {
        return service;
    }

    public void setService(ReceitaProvisionadaService service) {
        this.service = service;
    }
}
