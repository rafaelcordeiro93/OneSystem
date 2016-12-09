package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.war.service.DepositoService;
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
public class SelecaoDepositoView implements Serializable {

    private Deposito depositoSelecionada;
    private List<Deposito> depositoLista;
    private List<Deposito> depositosFiltrados;

    @ManagedProperty("#{depositoService}")
    private DepositoService service;

    @PostConstruct
    public void init() {
        depositoLista = service.buscarDepositos();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoDeposito", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(depositoSelecionada);
    }

    public Deposito getDepositoSelecionada() {
        return depositoSelecionada;
    }

    public void setDepositoSelecionada(Deposito depositoSelecionada) {
        this.depositoSelecionada = depositoSelecionada;
    }

    public List<Deposito> getDepositoLista() {
        return depositoLista;
    }

    public void setDepositoLista(List<Deposito> depositoLista) {
        this.depositoLista = depositoLista;
    }

    public List<Deposito> getDepositosFiltrados() {
        return depositosFiltrados;
    }

    public void setDepositosFiltrados(List<Deposito> depositosFiltrados) {
        this.depositosFiltrados = depositosFiltrados;
    }

    public DepositoService getService() {
        return service;
    }

    public void setService(DepositoService service) {
        this.service = service;
    }
}
