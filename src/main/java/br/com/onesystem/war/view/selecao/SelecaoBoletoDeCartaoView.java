package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.war.service.BoletoDeCartaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoBoletoDeCartaoView extends BasicCrudMBImpl<BoletoDeCartao> implements Serializable {

    @ManagedProperty("#{boletoDeCartaoService}")
    private BoletoDeCartaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarBoletoDeCartaos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoBoletoDeCartao");
    }

    public BoletoDeCartaoService getService() {
        return service;
    }

    public void setService(BoletoDeCartaoService service) {
        this.service = service;
    }
}
