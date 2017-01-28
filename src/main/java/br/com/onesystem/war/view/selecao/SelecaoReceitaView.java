package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Receita;
import br.com.onesystem.war.service.ReceitaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoReceitaView extends BasicCrudMBImpl<Receita> implements Serializable {

    @ManagedProperty("#{receitaService}")
    private ReceitaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarReceitas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoReceita");
    }

    public ReceitaService getService() {
        return service;
    }

    public void setService(ReceitaService service) {
        this.service = service;
    }
}
