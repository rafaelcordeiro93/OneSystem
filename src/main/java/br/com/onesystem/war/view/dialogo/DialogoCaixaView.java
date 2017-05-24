package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.CaixaDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.CaixaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.util.UsuarioLogadoUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoCaixaView extends BasicMBImpl<Caixa, CaixaBV> implements Serializable {

    private Caixa caixa;
    private List<Caixa> caixas;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void limparJanela() {
        e = new CaixaBV();
        caixa = null;
        caixas = new ArrayList<>();
        popularLista();
        try {
            buscaCaixaLogada();
        } catch (FDadoInvalidoException ex) {
           ex.print();
        }
    }

    private void popularLista() {
        caixas = new CaixaDAO().buscarCaixas().porUsuario(new UsuarioLogadoUtil().getEmailUsuario()).porEmAberto().listaDeResultados();
    }

    private void buscaCaixaLogada() throws FDadoInvalidoException {
        try {
            e = new CaixaBV(buscaCaixaNaSessao());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void logarCaixa() throws FDadoInvalidoException {
        try {
            SessionUtil.remove("caixa", FacesContext.getCurrentInstance());
            SessionUtil.put(caixa, "caixa", FacesContext.getCurrentInstance());
            limparJanela();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void deslogarCaixa() throws FDadoInvalidoException {
        try {
            SessionUtil.remove("caixa", FacesContext.getCurrentInstance());
            limparJanela();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Caixa buscaCaixaNaSessao() throws FDadoInvalidoException {
        try {
            Caixa c = (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
            return c;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public boolean temCaixa() throws FDadoInvalidoException {
        Caixa c = buscaCaixaNaSessao();
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
        opcoes.put("width", "45%");
        opcoes.put("draggable", false);
        opcoes.put("height", 300);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoCaixa", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public List<Caixa> getCaixas() {
        return caixas;
    }

    public void setCaixas(List<Caixa> caixas) {
        this.caixas = caixas;
    }

}
