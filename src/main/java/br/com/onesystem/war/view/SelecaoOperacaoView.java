package br.com.onesystem.war.view;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.war.service.OperacaoService;
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
public class SelecaoOperacaoView implements Serializable {

    private Operacao operacaoSelecionada;
    private List<Operacao> operacaoLista;
    private List<Operacao> operacaoFiltradas;

    @ManagedProperty("#{operacaoService}")
    private OperacaoService service;

    @PostConstruct
    public void init() {
        
        operacaoLista = service.buscarOperacao();
        for(Operacao p : operacaoLista){
            System.out.println( p );
        }
        
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoOperacao", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(operacaoSelecionada);
    }

    public Operacao getOperacaoSelecionada() {
        return operacaoSelecionada;
    }

    public void setOperacaoSelecionada(Operacao operacaoSelecionada) {
        this.operacaoSelecionada = operacaoSelecionada;
    }

    public List<Operacao> getOperacaoLista() {
        return operacaoLista;
    }

    public void setOperacaoLista(List<Operacao> operacaoLista) {
        this.operacaoLista = operacaoLista;
    }

    public List<Operacao> getOperacaoFiltradas() {
        return operacaoFiltradas;
    }

    public void setOperacaoFiltradas(List<Operacao> operacaoFiltradas) {
        this.operacaoFiltradas = operacaoFiltradas;
    }

    public OperacaoService getService() {
        return service;
    }

    public void setService(OperacaoService service) {
        this.service = service;
    }
}
