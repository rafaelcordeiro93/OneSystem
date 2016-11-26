package br.com.onesystem.war.view;

import br.com.onesystem.domain.Despesa;
import br.com.onesystem.war.service.DespesaService;
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
public class SelecaoDespesaView implements Serializable {

    private Despesa despesaSelecionada;
    private List<Despesa> despesaLista;
    private List<Despesa> despesasFiltradas;

    @ManagedProperty("#{despesaService}")
    private DespesaService service;

    @PostConstruct
    public void init() {
        despesaLista = service.buscarDespesas();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoDespesa", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(despesaSelecionada);
    }

    public Despesa getDespesaSelecionada() {
        return despesaSelecionada;
    }

    public void setDespesaSelecionada(Despesa despesaSelecionada) {
        this.despesaSelecionada = despesaSelecionada;
    }

    public List<Despesa> getDespesaLista() {
        return despesaLista;
    }

    public void setDespesaLista(List<Despesa> despesaLista) {
        this.despesaLista = despesaLista;
    }

    public List<Despesa> getDespesasFiltradas() {
        return despesasFiltradas;
    }

    public void setDespesasFiltradas(List<Despesa> despesasFiltradas) {
        this.despesasFiltradas = despesasFiltradas;
    }

    public DespesaService getService() {
        return service;
    }

    public void setService(DespesaService service) {
        this.service = service;
    }
}
