package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.war.service.ReceitaProvisionadaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoReceitaProvisionadaView extends BasicCrudMBImpl<ReceitaProvisionada> implements Serializable {

    @ManagedProperty("#{receitaProvisionadaService}")
    private ReceitaProvisionadaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarReceitaProvisionadasAReceber();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoReceitaProvisionada");
    }

    public ReceitaProvisionadaService getService() {
        return service;
    }

    public void setService(ReceitaProvisionadaService service) {
        this.service = service;
    }
}
