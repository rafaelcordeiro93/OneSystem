/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.Item;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named(value = "quantidadeDeItemView")
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class QuantidadeDeItemView extends BasicCrudMBImpl<QuantidadeDeItemPorDeposito> implements Serializable {

    private QuantidadeDeItemPorDeposito quantidadeDeItem = new QuantidadeDeItemPorDeposito();
    private QuantidadeDeItemPorDeposito quantidadeDeItemSelecionada;
    private List<QuantidadeDeItemPorDeposito> lista;
    private List<QuantidadeDeItemPorDeposito> listaFiltrada;
    private List<SaldoDeEstoque> listaDeEstoque;

    @Inject
    private EstoqueService serviceEstoque;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);

        lista = (List<QuantidadeDeItemPorDeposito>) session.getAttribute("onesystem.quantidadeLista.token");
        if (lista == null || lista.isEmpty()) {
            Item item = (Item) session.getAttribute("onesystem.item.token");
            listaDeEstoque = serviceEstoque.buscaListaDeSaldoDeEstoque(item, null);
            criaLista();
        }
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoQuantidadeDeItem");
    }

    public void exibirNaTela(String selecao) {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", true);
        opcoes.put("responsive", true);
        opcoes.put("width", 400);
        opcoes.put("height", 300);
        opcoes.put("draggable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("closable", true);
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/menu/estoque/selecao/" + selecao, opcoes, null);
    }

    @Override
    public String abrirEdicao() {
        return "/menu/estoque/item";
    }

    public void criaLista() {
        lista = new ArrayList<QuantidadeDeItemPorDeposito>();
        for (SaldoDeEstoque saldo : listaDeEstoque) {
            quantidadeDeItem = new QuantidadeDeItemPorDeposito(new Long(lista.size() + 1), saldo, BigDecimal.ZERO);
            lista.add(quantidadeDeItem);
        }
    }

    @Override
    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(lista);
    }

    public QuantidadeDeItemPorDeposito getQuantidadeDeItem() {
        return quantidadeDeItem;
    }

    public void setQuantidadeDeItem(QuantidadeDeItemPorDeposito quantidadeDeItem) {
        this.quantidadeDeItem = quantidadeDeItem;
    }

    public List<QuantidadeDeItemPorDeposito> getLista() {
        return lista;
    }

    public void setLista(List<QuantidadeDeItemPorDeposito> lista) {
        this.lista = lista;
    }

    public List<SaldoDeEstoque> getListaDeEstoque() {
        return listaDeEstoque;
    }

    public void setListaDeEstoque(List<SaldoDeEstoque> listaDeEstoque) {
        this.listaDeEstoque = listaDeEstoque;
    }

    public EstoqueService getServiceEstoque() {
        return serviceEstoque;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

    public QuantidadeDeItemPorDeposito getQuantidadeDeItemSelecionada() {
        return quantidadeDeItemSelecionada;
    }

    public void setQuantidadeDeItemSelecionada(QuantidadeDeItemPorDeposito quantidadeDeItemSelecionada) {
        this.quantidadeDeItemSelecionada = quantidadeDeItemSelecionada;
    }

    public List<QuantidadeDeItemPorDeposito> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<QuantidadeDeItemPorDeposito> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    @Override
    public List<QuantidadeDeItemPorDeposito> complete(String query) {
        return null;
    }

}
