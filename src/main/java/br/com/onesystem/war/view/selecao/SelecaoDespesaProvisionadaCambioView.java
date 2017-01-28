package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoDespesaProvisionadaCambioView extends BasicCrudMBImpl<DespesaProvisionada> implements Serializable {

    @ManagedProperty("#{despesaProvisionadaService}")
    private DespesaProvisionadaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDespesaProvisionadasAPagarDivisaoLucro();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoDespesaProvisionadaCambio");
    }

    public DespesaProvisionadaService getService() {
        return service;
    }

    public void setService(DespesaProvisionadaService service) {
        this.service = service;
    }
}
