package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.war.service.FormaDeRecebimentoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoFormaDeRecebimentoView extends BasicCrudMBImpl<FormaDeRecebimento> implements Serializable {

    @ManagedProperty("#{formaDeRecebimentoService}")
    private FormaDeRecebimentoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarFormasDeRecebimento();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoFormaDeRecebimento");
    }

    public FormaDeRecebimentoService getService() {
        return service;
    }

    public void setService(FormaDeRecebimentoService service) {
        this.service = service;
    }
}
