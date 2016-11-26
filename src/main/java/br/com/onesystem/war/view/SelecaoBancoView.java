package br.com.onesystem.war.view;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.war.service.BancoService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SelecaoBancoView implements Serializable {

    private Banco bancoSelecionado;
    private List<Banco> bancoLista;
    private List<Banco> bancosFiltradas;

    @ManagedProperty("#{bancoService}")
    private BancoService service;

    @PostConstruct
    public void init() {
        bancoLista = service.buscarBancos();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoBanco", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(bancoSelecionado);
    }

    public Banco getBancoSelecionado() {
        return bancoSelecionado;
    }

    public void setBancoSelecionado(Banco bancoSelecionado) {
        this.bancoSelecionado = bancoSelecionado;
    }

    public List<Banco> getBancoLista() {
        return bancoLista;
    }

    public void setBancoLista(List<Banco> bancoLista) {
        this.bancoLista = bancoLista;
    }

    public List<Banco> getBancosFiltradas() {
        return bancosFiltradas;
    }

    public void setBancosFiltradas(List<Banco> bancosFiltradas) {
        this.bancosFiltradas = bancosFiltradas;
    }

    public BancoService getService() {
        return service;
    }

    public void setService(BancoService service) {
        this.service = service;
    }
}
