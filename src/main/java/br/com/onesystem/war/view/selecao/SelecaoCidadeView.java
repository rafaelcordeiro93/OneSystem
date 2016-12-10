package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.war.service.CidadeService;
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
public class SelecaoCidadeView implements Serializable {

    private Cidade cidadeSelecionada;
    private List<Cidade> cidadeLista;
    private List<Cidade> cidadesFiltrados;

    @ManagedProperty("#{cidadeService}")
    private CidadeService service;

    @PostConstruct
    public void init() {
        cidadeLista = service.buscarCidades();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoCidade", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(cidadeSelecionada);
    }

    public Cidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public void setCidadeSelecionada(Cidade cidadeSelecionada) {
        this.cidadeSelecionada = cidadeSelecionada;
    }

    public List<Cidade> getCidadeLista() {
        return cidadeLista;
    }

    public void setCidadeLista(List<Cidade> cidadeLista) {
        this.cidadeLista = cidadeLista;
    }

    public List<Cidade> getCidadesFiltrados() {
        return cidadesFiltrados;
    }

    public void setCidadesFiltrados(List<Cidade> cidadesFiltrados) {
        this.cidadesFiltrados = cidadesFiltrados;
    }

    public CidadeService getService() {
        return service;
    }

    public void setService(CidadeService service) {
        this.service = service;
    }
}
