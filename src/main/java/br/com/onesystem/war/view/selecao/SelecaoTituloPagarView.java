package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Titulo;
import br.com.onesystem.war.service.TituloService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoTituloPagarView extends BasicCrudMBImpl<Titulo> implements Serializable {

    @ManagedProperty("#{tituloService}")
    private TituloService service;

    @PostConstruct
    public void init() {
        beans = service.buscarTitulosAPagar();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoTituloPagar");
    }

    public TituloService getService() {
        return service;
    }

    public void setService(TituloService service) {
        this.service = service;
    }
}
