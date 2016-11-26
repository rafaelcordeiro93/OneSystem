package br.com.onesystem.war.view;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.war.service.ContaService;
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
public class SelecaoContaView implements Serializable {

    private Conta contaSelecionada;
    private List<Conta> contaLista;
    private List<Conta> contasFiltradas;

    @ManagedProperty("#{contaService}")
    private ContaService service;

    @PostConstruct
    public void init() {
        contaLista = service.buscarContas();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoConta", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(contaSelecionada);
    }

    public Conta getContaSelecionada() {
        return contaSelecionada;
    }

    public void setContaSelecionada(Conta contaSelecionada) {
        this.contaSelecionada = contaSelecionada;
    }

    public List<Conta> getContaLista() {
        return contaLista;
    }

    public void setContaLista(List<Conta> contaLista) {
        this.contaLista = contaLista;
    }

    public List<Conta> getContasFiltradas() {
        return contasFiltradas;
    }

    public void setContasFiltradas(List<Conta> contasFiltradas) {
        this.contasFiltradas = contasFiltradas;
    }

    public ContaService getService() {
        return service;
    }

    public void setService(ContaService service) {
        this.service = service;
    }
}
