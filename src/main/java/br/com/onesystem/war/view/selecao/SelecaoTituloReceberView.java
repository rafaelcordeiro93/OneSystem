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
public class SelecaoTituloReceberView extends BasicCrudMBImpl<Titulo> implements Serializable {

    @ManagedProperty("#{tituloService}")
    private TituloService service;

    @PostConstruct
    public void init() {
        beans = service.buscarTitulosAReceber();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoTituloReceber");
    }

    public TituloService getService() {
        return service;
    }

    public void setService(TituloService service) {
        this.service = service;
    }
}
