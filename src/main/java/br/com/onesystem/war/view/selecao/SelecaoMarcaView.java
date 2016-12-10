package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.war.service.MarcaService;
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
public class SelecaoMarcaView implements Serializable {

    private Marca marcaSelecionada;
    private List<Marca> marcaLista;
    private List<Marca> marcasFiltradas;

    @ManagedProperty("#{marcaService}")
    private MarcaService service;

    @PostConstruct
    public void init() {
        marcaLista = service.buscarMarcas();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoMarca", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(marcaSelecionada);
    }

    public Marca getMarcaSelecionada() {
        return marcaSelecionada;
    }

    public void setMarcaSelecionada(Marca marcaSelecionada) {
        this.marcaSelecionada = marcaSelecionada;
    }

    public List<Marca> getMarcaLista() {
        return marcaLista;
    }

    public void setMarcaLista(List<Marca> marcaLista) {
        this.marcaLista = marcaLista;
    }

    public List<Marca> getMarcasFiltradas() {
        return marcasFiltradas;
    }

    public void setMarcasFiltradas(List<Marca> marcasFiltradas) {
        this.marcasFiltradas = marcasFiltradas;
    }

    public MarcaService getService() {
        return service;
    }

    public void setService(MarcaService service) {
        this.service = service;
    }
}
