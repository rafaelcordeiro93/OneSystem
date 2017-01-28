package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.war.service.CidadeService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoCidadeView extends BasicCrudMBImpl<Cidade> implements Serializable {

    @ManagedProperty("#{cidadeService}")
    private CidadeService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCidades();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoCidade");
    }

    public CidadeService getService() {
        return service;
    }

    public void setService(CidadeService service) {
        this.service = service;
    }
}
