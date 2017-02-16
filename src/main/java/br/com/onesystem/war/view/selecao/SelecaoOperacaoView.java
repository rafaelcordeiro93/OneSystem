package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.war.service.OperacaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoOperacaoView extends BasicCrudMBImpl<Operacao> implements Serializable {

    @ManagedProperty("#{operacaoService}")
    private OperacaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscar();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoOperacao");
    }

    public OperacaoService getService() {
        return service;
    }

    public void setService(OperacaoService service) {
        this.service = service;
    }
}
