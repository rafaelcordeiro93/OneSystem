package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.war.service.ContaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoContaView extends BasicCrudMBImpl<Conta> implements Serializable {

    @ManagedProperty("#{contaService}")
    private ContaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarContas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoConta");
    }

    public ContaService getService() {
        return service;
    }

    public void setService(ContaService service) {
        this.service = service;
    }
}
