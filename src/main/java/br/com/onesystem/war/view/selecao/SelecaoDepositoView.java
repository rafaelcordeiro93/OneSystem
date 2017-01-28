package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.war.service.DepositoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoDepositoView extends BasicCrudMBImpl<Deposito> implements Serializable {

    @ManagedProperty("#{depositoService}")
    private DepositoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDepositos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoDeposito");
    }

    public DepositoService getService() {
        return service;
    }

    public void setService(DepositoService service) {
        this.service = service;
    }
}
