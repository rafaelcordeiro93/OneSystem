package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.war.service.CartaoService;
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
public class SelecaoCartaoView implements Serializable {

    private Cartao cartaoSelecionada;
    private List<Cartao> cartaoLista;
    private List<Cartao> cartaosFiltrados;

    @ManagedProperty("#{cartaoService}")
    private CartaoService service;

    @PostConstruct
    public void init() {
        cartaoLista = service.buscarCartaos();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoCartao", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(cartaoSelecionada);
    }

    public Cartao getCartaoSelecionada() {
        return cartaoSelecionada;
    }

    public void setCartaoSelecionada(Cartao cartaoSelecionada) {
        this.cartaoSelecionada = cartaoSelecionada;
    }

    public List<Cartao> getCartaoLista() {
        return cartaoLista;
    }

    public void setCartaoLista(List<Cartao> cartaoLista) {
        this.cartaoLista = cartaoLista;
    }

    public List<Cartao> getCartaosFiltrados() {
        return cartaosFiltrados;
    }

    public void setCartaosFiltrados(List<Cartao> cartaosFiltrados) {
        this.cartaosFiltrados = cartaosFiltrados;
    }

    public CartaoService getService() {
        return service;
    }

    public void setService(CartaoService service) {
        this.service = service;
    }
}
