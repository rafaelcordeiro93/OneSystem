package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.war.service.ListaDePrecoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoListaDePrecoView extends BasicCrudMBImpl<ListaDePreco> implements Serializable {

    @ManagedProperty("#{listaDePrecoService}")
    private ListaDePrecoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarListaPrecos();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoListaDePreco");
    }

    public ListaDePrecoService getService() {
        return service;
    }

    public void setService(ListaDePrecoService service) {
        this.service = service;
    }
}
