package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.war.service.CotacaoService;
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
public class SelecaoCotacaoView implements Serializable {

    private Cotacao cotacaoSelecionada;
    private List<Cotacao> cotacaoLista;
    private List<Cotacao> cotacaosFiltrados;

    @ManagedProperty("#{cotacaoService}")
    private CotacaoService service;

    @PostConstruct
    public void init() {
        cotacaoLista = service.buscarCotacoes();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoCotacao", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(cotacaoSelecionada);
    }

    public Cotacao getCotacaoSelecionada() {
        return cotacaoSelecionada;
    }

    public void setCotacaoSelecionada(Cotacao cotacaoSelecionada) {
        this.cotacaoSelecionada = cotacaoSelecionada;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public List<Cotacao> getCotacaosFiltrados() {
        return cotacaosFiltrados;
    }

    public void setCotacaosFiltrados(List<Cotacao> cotacaosFiltrados) {
        this.cotacaosFiltrados = cotacaosFiltrados;
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }
}
