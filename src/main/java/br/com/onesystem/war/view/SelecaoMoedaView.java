package br.com.onesystem.war.view;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.war.service.MoedaService;
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
public class SelecaoMoedaView implements Serializable {

    private Moeda moedaSelecionada;
    private List<Moeda> moedaLista;
    private List<Moeda> moedasFiltradas;

    @ManagedProperty("#{moedaService}")
    private MoedaService service;

    @PostConstruct
    public void init() {
        moedaLista = service.buscarMoedas();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoMoeda", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(moedaSelecionada);
    }

    public Moeda getMoedaSelecionada() {
        return moedaSelecionada;
    }

    public void setMoedaSelecionada(Moeda moedaSelecionada) {
        this.moedaSelecionada = moedaSelecionada;
    }

    public List<Moeda> getMoedaLista() {
        return moedaLista;
    }

    public void setMoedaLista(List<Moeda> moedaLista) {
        this.moedaLista = moedaLista;
    }

    public List<Moeda> getMoedasFiltradas() {
        return moedasFiltradas;
    }

    public void setMoedasFiltradas(List<Moeda> moedasFiltradas) {
        this.moedasFiltradas = moedasFiltradas;
    }

    public MoedaService getService() {
        return service;
    }

    public void setService(MoedaService service) {
        this.service = service;
    }
}
