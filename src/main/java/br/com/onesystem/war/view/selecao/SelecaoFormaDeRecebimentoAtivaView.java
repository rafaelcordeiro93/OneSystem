package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.war.service.FormaDeRecebimentoService;
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
public class SelecaoFormaDeRecebimentoAtivaView implements Serializable {

    private FormaDeRecebimento formaDeRecebimentoSelecionado;
    private List<FormaDeRecebimento> formaDeRecebimentoLista;
    private List<FormaDeRecebimento> formaDeRecebimentosFiltradas;

    @ManagedProperty("#{formaDeRecebimentoService}")
    private FormaDeRecebimentoService service;

    @PostConstruct
    public void init() {
        formaDeRecebimentoLista = service.buscarFormasDeRecebimentoAtivas();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoFormaDeRecebimentoAtiva", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(formaDeRecebimentoSelecionado);
    }

    public FormaDeRecebimento getFormaDeRecebimentoSelecionado() {
        return formaDeRecebimentoSelecionado;
    }

    public void setFormaDeRecebimentoSelecionado(FormaDeRecebimento formaDeRecebimentoSelecionado) {
        this.formaDeRecebimentoSelecionado = formaDeRecebimentoSelecionado;
    }

    public List<FormaDeRecebimento> getFormaDeRecebimentoLista() {
        return formaDeRecebimentoLista;
    }

    public void setFormaDeRecebimentoLista(List<FormaDeRecebimento> formaDeRecebimentoLista) {
        this.formaDeRecebimentoLista = formaDeRecebimentoLista;
    }

    public List<FormaDeRecebimento> getFormaDeRecebimentosFiltradas() {
        return formaDeRecebimentosFiltradas;
    }

    public void setFormaDeRecebimentosFiltradas(List<FormaDeRecebimento> formaDeRecebimentosFiltradas) {
        this.formaDeRecebimentosFiltradas = formaDeRecebimentosFiltradas;
    }

    public FormaDeRecebimentoService getService() {
        return service;
    }

    public void setService(FormaDeRecebimentoService service) {
        this.service = service;
    }
}
