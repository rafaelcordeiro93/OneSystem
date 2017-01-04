package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.war.view.selecao.*;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.reportTemplate.CotacaoValores;
import br.com.onesystem.war.service.CotacaoService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class CotacaoValoresView implements Serializable {

    private CotacaoValores cotacaoValoresSelecionado;
    private List<Cotacao> cotacaoLista;
    private List<CotacaoValores> cotacoes;

    @ManagedProperty("#{cotacaoService}")
    private CotacaoService service;

    @PostConstruct
    public void init() {
        cotacaoLista = service.buscarCotacoes();
        cotacoes = new ArrayList<CotacaoValores>();
        for (Cotacao c : cotacaoLista) {
            cotacoes.add(new CotacaoValores(c, null, null, null));
        }
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecao/selecaoCotacao", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(cotacaoValoresSelecionado);
    }

    public CotacaoValores getCotacaoValoresSelecionado() {
        return cotacaoValoresSelecionado;
    }

    public void setCotacaoValoresSelecionado(CotacaoValores cotacaoValoresSelecionado) {
        this.cotacaoValoresSelecionado = cotacaoValoresSelecionado;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public CotacaoService getService() {
        return service;
    }

    public void setService(CotacaoService service) {
        this.service = service;
    }

    public List<CotacaoValores> getCotacoes() {
        return cotacoes;
    }

    public void setCotacoes(List<CotacaoValores> cotacoes) {
        this.cotacoes = cotacoes;
    }

}
