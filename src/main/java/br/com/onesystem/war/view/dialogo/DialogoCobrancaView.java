package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoCobrancaView extends BasicMBImpl<Cobranca, CobrancaBV> implements Serializable {
    
    private Cobranca cobranca;
    
    @PostConstruct
    public void init() {
        try {
            limparJanela();
            buscaDaSessao();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    private void buscaDaSessao() throws FDadoInvalidoException {
        cobranca = (Cobranca) SessionUtil.getObject("cobranca", FacesContext.getCurrentInstance());
        if (cobranca != null) {
            e = new CobrancaBV(cobranca);
        } else {
            e = new CobrancaBV();
            e.setId((Long) SessionUtil.getObject("idCobranca", FacesContext.getCurrentInstance()));
        }
    }
    
    public void abrirDialogo() {
        exibeNaTela();
    }
    
    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 400);
        opcoes.put("draggable", true);
        opcoes.put("height", 525);
        opcoes.put("closable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");
        
        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoCobranca", opcoes, null);
    }
    
    public List<ModalidadeDeCobranca> getModalidadesDeCobranca() {
        return Arrays.asList(ModalidadeDeCobranca.values());
    }
    
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Cartao) {
            e.setCartao((Cartao) obj);
        } else if (obj instanceof Banco) {
            e.setBanco((Banco) obj);
        }
    }
    
    public void salvar() {
        try {
            removeDaSessao();
            Cobranca c = constroi();
            RequestContext.getCurrentInstance().closeDialog(c);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    private Cobranca constroi() throws DadoInvalidoException {
        Cobranca c = null;
        switch (e.getModalidadeDeCobranca()) {
            case CARTAO:
                c = e.construirBoletoDeCartaoComId();
                break;
            case CHEQUE:
                c = e.construirChequeComID();
                break;
            case CREDITO:
                c = e.construirCreditoComID();
                break;
            case TITULO:
                c = e.construirTituloComID();
                break;
        }
        return c;
    }
    
    private void removeDaSessao() throws FDadoInvalidoException {
        SessionUtil.remove("cobranca", FacesContext.getCurrentInstance());
    }
    
    @Override
    public void limparJanela() {
        e = new CobrancaBV();
        cobranca = null;
    }
    
}
