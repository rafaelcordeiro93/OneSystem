package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.war.service.ChequeService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoChequeView extends BasicCrudMBImpl<Cheque> implements Serializable {

    @ManagedProperty("#{chequeService}")
    private ChequeService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCheques();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoCheque");
    }

    public ChequeService getService() {
        return service;
    }

    public void setService(ChequeService service) {
        this.service = service;
    }
}
