package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.war.service.DespesaProvisionadaService;
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
public class SelecaoDespesaProvisionadaCambioView implements Serializable {

    private DespesaProvisionada despesaProvisionadaSelecionada;
    private List<DespesaProvisionada> despesaProvisionadaLista;
    private List<DespesaProvisionada> despesasProvisionadasFiltradas;

    @ManagedProperty("#{despesaProvisionadaService}")
    private DespesaProvisionadaService service;

    @PostConstruct
    public void init() {
        despesaProvisionadaLista = service.buscarDespesaProvisionadasAPagarDivisaoLucro();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoDespesaProvisionadaCambio", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(despesaProvisionadaSelecionada);
    }

    public DespesaProvisionada getDespesaProvisionadaSelecionada() {
        return despesaProvisionadaSelecionada;
    }

    public void setDespesaProvisionadaSelecionada(DespesaProvisionada despesaProvisionadaSelecionada) {
        this.despesaProvisionadaSelecionada = despesaProvisionadaSelecionada;
    }

    public List<DespesaProvisionada> getDespesaProvisionadaLista() {
        return despesaProvisionadaLista;
    }

    public void setDespesaProvisionadaLista(List<DespesaProvisionada> despesaProvisionadaLista) {
        this.despesaProvisionadaLista = despesaProvisionadaLista;
    }

    public List<DespesaProvisionada> getDespesasProvisionadasFiltradas() {
        return despesasProvisionadasFiltradas;
    }

    public void setDespesasProvisionadasFiltradas(List<DespesaProvisionada> despesasProvisionadasFiltradas) {
        this.despesasProvisionadasFiltradas = despesasProvisionadasFiltradas;
    }

    public DespesaProvisionadaService getService() {
        return service;
    }

    public void setService(DespesaProvisionadaService service) {
        this.service = service;
    }
}
