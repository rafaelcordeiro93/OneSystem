package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.CaixaBV;
import br.com.onesystem.war.builder.ItemDeComandaBV;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
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
//            buscaDaSessao();
//            populaCampos();
    }
    
     @Override
    public void limparJanela() {
        e = new CaixaBV();
        caixa = null;
        caixas = new ArrayList<>();
    }

  

//    private void buscaDaSessao() throws FDadoInvalidoException {
//        comanda = (Comanda) SessionUtil.getObject("comanda", FacesContext.getCurrentInstance());
//        tipoOperacao = (TipoOperacao) SessionUtil.getObject("tipoOperacao", FacesContext.getCurrentInstance());
//    }

    private void populaCampos() {
      
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", "90%");
        opcoes.put("draggable", false);
        opcoes.put("height", 600);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoCaixa", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof List) {
            
        }
    }

    public void atribuiItemASessao(ItemDeComandaBV itemDeComanda) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.quantidadeLista.token");
        session.setAttribute("onesystem.quantidadeLista.token", itemDeComanda.getListaDeQuantidade());

    }

  
    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("nota", FacesContext.getCurrentInstance());
        SessionUtil.remove("tipoOperacao", FacesContext.getCurrentInstance());
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
