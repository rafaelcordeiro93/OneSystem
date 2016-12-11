package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
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
public class SelecaoAjusteDeEstoqueView implements Serializable {

    private AjusteDeEstoque ajusteDeEstoqueSelecionada;
    private List<AjusteDeEstoque> ajusteDeEstoqueLista;
    private List<AjusteDeEstoque> ajusteDeEstoquesFiltrados;

    @ManagedProperty("#{ajusteDeEstoqueService}")
    private AjusteDeEstoqueService service;

    @PostConstruct
    public void init() {
        ajusteDeEstoqueLista = service.buscarAjusteDeEstoques();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoAjusteDeEstoque", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(ajusteDeEstoqueSelecionada);
    }

    public AjusteDeEstoque getAjusteDeEstoqueSelecionada() {
        return ajusteDeEstoqueSelecionada;
    }

    public void setAjusteDeEstoqueSelecionada(AjusteDeEstoque ajusteDeEstoqueSelecionada) {
        this.ajusteDeEstoqueSelecionada = ajusteDeEstoqueSelecionada;
    }

    public List<AjusteDeEstoque> getAjusteDeEstoqueLista() {
        return ajusteDeEstoqueLista;
    }

    public void setAjusteDeEstoqueLista(List<AjusteDeEstoque> ajusteDeEstoqueLista) {
        this.ajusteDeEstoqueLista = ajusteDeEstoqueLista;
    }

    public List<AjusteDeEstoque> getAjusteDeEstoquesFiltrados() {
        return ajusteDeEstoquesFiltrados;
    }

    public void setAjusteDeEstoquesFiltrados(List<AjusteDeEstoque> ajusteDeEstoquesFiltrados) {
        this.ajusteDeEstoquesFiltrados = ajusteDeEstoquesFiltrados;
    }

    public AjusteDeEstoqueService getService() {
        return service;
    }

    public void setService(AjusteDeEstoqueService service) {
        this.service = service;
    }
}
