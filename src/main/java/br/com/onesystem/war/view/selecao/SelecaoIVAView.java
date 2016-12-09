package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.IVA;
import br.com.onesystem.war.service.IVAService;
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
public class SelecaoIVAView implements Serializable {

    private IVA iVASelecionada;
    private List<IVA> iVALista;
    private List<IVA> iVAFiltradas;

    @ManagedProperty("#{ivaService}")
    private IVAService service;

    @PostConstruct
    public void init() {
        iVALista = service.buscarIVAs();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoIVA", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(iVASelecionada);
    }

    public IVA getIVASelecionada() {
        return iVASelecionada;
    }

    public void setIVASelecionada(IVA iVASelecionada) {
        this.iVASelecionada = iVASelecionada;
    }

    public List<IVA> getIVALista() {
        return iVALista;
    }

    public void setIVALista(List<IVA> iVALista) {
        this.iVALista = iVALista;
    }

    public List<IVA> getIVAFiltradas() {
        return iVAFiltradas;
    }

    public void setIVAsFiltradas(List<IVA> iVAsFiltradas) {
        this.iVAFiltradas = iVAsFiltradas;
    }

    public IVAService getService() {
        return service;
    }

    public void setService(IVAService service) {
        this.service = service;
    }
}
