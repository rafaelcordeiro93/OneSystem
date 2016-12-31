package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.war.service.ChequeService;
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
public class SelecaoChequeView implements Serializable {

    private Cheque chequeSelecionada;
    private List<Cheque> chequeLista;
    private List<Cheque> chequesFiltrados;

    @ManagedProperty("#{chequeService}")
    private ChequeService service;

    @PostConstruct
    public void init() {
        chequeLista = service.buscarCheques();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoCheque", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(chequeSelecionada);
    }

    public Cheque getChequeSelecionada() {
        return chequeSelecionada;
    }

    public void setChequeSelecionada(Cheque chequeSelecionada) {
        this.chequeSelecionada = chequeSelecionada;
    }

    public List<Cheque> getChequeLista() {
        return chequeLista;
    }

    public void setChequeLista(List<Cheque> chequeLista) {
        this.chequeLista = chequeLista;
    }

    public List<Cheque> getChequesFiltrados() {
        return chequesFiltrados;
    }

    public void setChequesFiltrados(List<Cheque> chequesFiltrados) {
        this.chequesFiltrados = chequesFiltrados;
    }

    public ChequeService getService() {
        return service;
    }

    public void setService(ChequeService service) {
        this.service = service;
    }
}
