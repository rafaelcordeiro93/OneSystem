package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoAjusteDeEstoqueView extends BasicCrudMBImpl<AjusteDeEstoque> implements Serializable {

    @ManagedProperty("#{ajusteDeEstoqueService}")
    private AjusteDeEstoqueService service;

    @PostConstruct
    public void init() {
        beans = service.buscarAjusteDeEstoques();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoAjusteDeEstoque");
    }

    public AjusteDeEstoqueService getService() {
        return service;
    }

    public void setService(AjusteDeEstoqueService service) {
        this.service = service;
    }
}
