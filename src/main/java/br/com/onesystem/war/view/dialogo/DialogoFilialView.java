package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.FilialDAO;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.FilialBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoFilialView extends BasicMBImpl<Filial, FilialBV> implements Serializable {

    private Filial filial;
    private List<Filial> filiais;

    @Inject
    private FilialDAO dao;
    
    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void limparJanela() {
        e = new FilialBV();
        filial = null;
        filiais = new ArrayList<>();
        popularLista();
        try {
            buscaFilialLogada();
        } catch (FDadoInvalidoException ex) {
            ex.print();
        }
    }

    private void popularLista() {
        filiais = dao.listaDeResultados();
    }

    public void reloadPage() throws IOException {
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        RequestContext.getCurrentInstance().closeDialog("dialogo/dialogoFilial");               
//        ec.redirect(ec.getRequestContextPath() + "/dashboard.xhtml");
        RequestContext.getCurrentInstance().closeDialog("redireciona");
    }

    private void buscaFilialLogada() throws FDadoInvalidoException {
        try {
            e = new FilialBV(buscaFilialNaSessao());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void logarFilial() throws FDadoInvalidoException {
        try {
            SessionUtil.remove("filial", FacesContext.getCurrentInstance());
            SessionUtil.put(filial, "filial", FacesContext.getCurrentInstance());
            reloadPage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void deslogarFilial() throws FDadoInvalidoException {
        try {
            SessionUtil.remove("filial", FacesContext.getCurrentInstance());
            reloadPage();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Filial buscaFilialNaSessao() throws FDadoInvalidoException {
        try {
            Filial c = (Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance());
            return c;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public boolean temFilial() throws FDadoInvalidoException {
        Filial c = buscaFilialNaSessao();
        if (c != null) {
            return false;
        } else {
            return true;
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", "60%");
        opcoes.put("draggable", false);
        opcoes.put("height", 300);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoFilial", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public List<Filial> getFiliais() {
        return filiais;
    }

    public void setFiliais(List<Filial> filiais) {
        this.filiais = filiais;
    }

}
