package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.war.service.BoletoDeCartaoService;
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
public class SelecaoBoletoDeCartaoView implements Serializable {

    private BoletoDeCartao boletoDeCartaoSelecionada;
    private List<BoletoDeCartao> boletoDeCartaoLista;
    private List<BoletoDeCartao> boletoDeCartaosFiltrados;

    @ManagedProperty("#{boletoDeCartaoService}")
    private BoletoDeCartaoService service;

    @PostConstruct
    public void init() {
        boletoDeCartaoLista = service.buscarBoletoDeCartaos();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoBoletoDeCartao", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(boletoDeCartaoSelecionada);
    }

    public BoletoDeCartao getBoletoDeCartaoSelecionada() {
        return boletoDeCartaoSelecionada;
    }

    public void setBoletoDeCartaoSelecionada(BoletoDeCartao boletoDeCartaoSelecionada) {
        this.boletoDeCartaoSelecionada = boletoDeCartaoSelecionada;
    }

    public List<BoletoDeCartao> getBoletoDeCartaoLista() {
        return boletoDeCartaoLista;
    }

    public void setBoletoDeCartaoLista(List<BoletoDeCartao> boletoDeCartaoLista) {
        this.boletoDeCartaoLista = boletoDeCartaoLista;
    }

    public List<BoletoDeCartao> getBoletoDeCartaosFiltrados() {
        return boletoDeCartaosFiltrados;
    }

    public void setBoletoDeCartaosFiltrados(List<BoletoDeCartao> boletoDeCartaosFiltrados) {
        this.boletoDeCartaosFiltrados = boletoDeCartaosFiltrados;
    }

    public BoletoDeCartaoService getService() {
        return service;
    }

    public void setService(BoletoDeCartaoService service) {
        this.service = service;
    }
}
