package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.war.service.GrupoFinanceiroService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoGrupoFinanceiroReceitaView extends BasicCrudMBImpl<GrupoFinanceiro> implements Serializable {

    @ManagedProperty("#{grupoFinanceiroService}")
    private GrupoFinanceiroService service;

    @PostConstruct
    public void init() {
        beans = service.buscarGruposDeReceitas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoGrupoFinanceiroReceita");
    }

    public GrupoFinanceiroService getService() {
        return service;
    }

    public void setService(GrupoFinanceiroService service) {
        this.service = service;
    }
}
