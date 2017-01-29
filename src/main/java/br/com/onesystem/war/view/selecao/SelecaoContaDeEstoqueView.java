package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.war.service.ContaDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoContaDeEstoqueView extends BasicCrudMBImpl<ContaDeEstoque> implements Serializable {

    @ManagedProperty("#{contaDeEstoqueService}")
    private ContaDeEstoqueService service;

    @PostConstruct
    public void init() {
        beans = service.buscarContaDeEstoque();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoContaDeEstoque");
    }

    public ContaDeEstoqueService getService() {
        return service;
    }

    public void setService(ContaDeEstoqueService service) {
        this.service = service;
    }
}
