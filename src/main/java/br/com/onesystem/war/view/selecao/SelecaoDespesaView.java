package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Despesa;
import br.com.onesystem.war.service.DespesaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoDespesaView extends BasicCrudMBImpl<Despesa> implements Serializable {

    @ManagedProperty("#{despesaService}")
    private DespesaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarDespesas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoDespesa");
    }

    public DespesaService getService() {
        return service;
    }

    public void setService(DespesaService service) {
        this.service = service;
    }
}
