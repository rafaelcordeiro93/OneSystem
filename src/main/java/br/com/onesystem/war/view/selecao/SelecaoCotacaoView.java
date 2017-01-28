package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoCotacaoView extends BasicCrudMBImpl<Cotacao> implements Serializable {

    @ManagedProperty("#{cotacaoService}")
    private CotacaoService service;

    @PostConstruct
    public void init() {
        beans = service.buscarCotacoes();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoCotacao");
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }
}
