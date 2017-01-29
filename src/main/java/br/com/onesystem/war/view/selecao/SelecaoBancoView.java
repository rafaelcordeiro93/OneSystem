package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.war.service.BancoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoBancoView extends BasicCrudMBImpl<Banco> implements Serializable {

    @ManagedProperty("#{bancoService}")
    private BancoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarBancos();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoBanco");
    }

    public BancoService getService() {
        return service;
    }

    public void setService(BancoService service) {
        this.service = service;
    }
}
