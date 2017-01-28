package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.war.service.CartaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoCartaoView extends BasicCrudMBImpl<Cartao> implements Serializable {

    @ManagedProperty("#{cartaoService}")
    private CartaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCartaos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoCartao");
    }

    public CartaoService getService() {
        return service;
    }

    public void setService(CartaoService service) {
        this.service = service;
    }
}
