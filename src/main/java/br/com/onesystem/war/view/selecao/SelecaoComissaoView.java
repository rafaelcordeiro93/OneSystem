package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.war.service.ComissaoService;
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
public class SelecaoComissaoView implements Serializable {

    private Comissao comissaoSelecionada;
    private List<Comissao> comissaoLista;
    private List<Comissao> comissaosFiltrados;

    @ManagedProperty("#{comissaoService}")
    private ComissaoService service;

    @PostConstruct
    public void init() {
        comissaoLista = service.buscarComissao();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoComissao", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(comissaoSelecionada);
    }

    public Comissao getComissaoSelecionada() {
        return comissaoSelecionada;
    }

    public void setComissaoSelecionada(Comissao comissaoSelecionada) {
        this.comissaoSelecionada = comissaoSelecionada;
    }

    public List<Comissao> getComissaoLista() {
        return comissaoLista;
    }

    public void setComissaoLista(List<Comissao> comissaoLista) {
        this.comissaoLista = comissaoLista;
    }

    public List<Comissao> getComissaosFiltrados() {
        return comissaosFiltrados;
    }

    public void setComissaosFiltrados(List<Comissao> comissaosFiltrados) {
        this.comissaosFiltrados = comissaosFiltrados;
    }

    public ComissaoService getService() {
        return service;
    }

    public void setService(ComissaoService service) {
        this.service = service;
    }
}
