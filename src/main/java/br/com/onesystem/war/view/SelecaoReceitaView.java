package br.com.onesystem.war.view;

import br.com.onesystem.domain.Receita;
import br.com.onesystem.war.service.ReceitaService;
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
public class SelecaoReceitaView implements Serializable {

    private Receita receitaSelecionada;
    private List<Receita> receitaLista;
    private List<Receita> receitasFiltradas;

    @ManagedProperty("#{receitaService}")
    private ReceitaService service;

    @PostConstruct
    public void init() {
        receitaLista = service.buscarReceitas();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoReceita", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(receitaSelecionada);
    }

    public Receita getReceitaSelecionada() {
        return receitaSelecionada;
    }

    public void setReceitaSelecionada(Receita receitaSelecionada) {
        this.receitaSelecionada = receitaSelecionada;
    }

    public List<Receita> getReceitaLista() {
        return receitaLista;
    }

    public void setReceitaLista(List<Receita> receitaLista) {
        this.receitaLista = receitaLista;
    }

    public List<Receita> getReceitasFiltradas() {
        return receitasFiltradas;
    }

    public void setReceitasFiltradas(List<Receita> receitasFiltradas) {
        this.receitasFiltradas = receitasFiltradas;
    }

    public ReceitaService getService() {
        return service;
    }

    public void setService(ReceitaService service) {
        this.service = service;
    }
}
