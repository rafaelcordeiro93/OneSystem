package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.war.service.MarcaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoMarcaView extends BasicCrudMBImpl<Marca> implements Serializable {

    @ManagedProperty("#{marcaService}")
    private MarcaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarMarcas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoMarca");
    }

    public MarcaService getService() {
        return service;
    }

    public void setService(MarcaService service) {
        this.service = service;
    }
}
