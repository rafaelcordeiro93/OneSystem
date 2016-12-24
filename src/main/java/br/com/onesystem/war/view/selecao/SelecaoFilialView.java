package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.war.service.FilialService;
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
public class SelecaoFilialView implements Serializable {

    private Filial filialSelecionada;
    private List<Filial> filialLista;
    private List<Filial> filialsFiltrados;

    @ManagedProperty("#{filialService}")
    private FilialService service;

    @PostConstruct
    public void init() {
        filialLista = service.buscarFilials();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoFilial", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(filialSelecionada);
    }

    public Filial getFilialSelecionada() {
        return filialSelecionada;
    }

    public void setFilialSelecionada(Filial filialSelecionada) {
        this.filialSelecionada = filialSelecionada;
    }

    public List<Filial> getFilialLista() {
        return filialLista;
    }

    public void setFilialLista(List<Filial> filialLista) {
        this.filialLista = filialLista;
    }

    public List<Filial> getFilialsFiltrados() {
        return filialsFiltrados;
    }

    public void setFilialsFiltrados(List<Filial> filialsFiltrados) {
        this.filialsFiltrados = filialsFiltrados;
    }

    public FilialService getService() {
        return service;
    }

    public void setService(FilialService service) {
        this.service = service;
    }
}
