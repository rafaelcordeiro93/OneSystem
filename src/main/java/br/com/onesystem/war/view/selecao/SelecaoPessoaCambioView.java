package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoPessoaCambioView extends BasicCrudMBImpl<Pessoa> implements Serializable {

    @ManagedProperty("#{configuracaoCambioService}")
    private ConfiguracaoCambioService service;

    @PostConstruct
    public void init() {
        beans = service.buscarPessoas();
    }

    public void abrirDialogo() {
        exibirNaTela("selecaoPessoaCambio");
    }

    public ConfiguracaoCambioService getService() {
        return service;
    }

    public void setService(ConfiguracaoCambioService service) {
        this.service = service;
    }
}
