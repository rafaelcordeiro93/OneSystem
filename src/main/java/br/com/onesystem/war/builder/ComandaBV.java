/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.builder.ComandaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ComandaBV implements Serializable, BuilderView<Comanda> {

    private Long id;
    private List<ItemDeComanda> itensDeComanda = new ArrayList<>();
    private ListaDePreco listaDePreco;
    private Cotacao cotacao;
    private String historico;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;
    private Integer numeroComanda;
    private BigDecimal porcentagemAcrescimo;
    private BigDecimal porcentagemDesconto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemDeComanda> getItensDeComanda() {
        return itensDeComanda;
    }

    public void setItensDeComanda(List<ItemDeComanda> itensDeComanda) {
        this.itensDeComanda = itensDeComanda;
    }

    public ListaDePreco getListaDePreco() {
        return listaDePreco;
    }

    public void setListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public void setDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public Integer getNumeroComanda() {
        return numeroComanda;
    }

    public void setNumeroComanda(Integer numeroComanda) {
        this.numeroComanda = numeroComanda;
    }

    public BigDecimal getPorcentagemAcrescimo() {
        return porcentagemAcrescimo;
    }

    public void setPorcentagemAcrescimo(BigDecimal porcentagemAcrescimo) {
        this.porcentagemAcrescimo = porcentagemAcrescimo;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public Comanda construir() throws DadoInvalidoException {
        return new ComandaBuilder().comAcrescimo(acrescimo).comCotacao(cotacao).comDesconto(desconto)
                .comDespesaCobranca(despesaCobranca).comFrete(frete).comHistorico(historico).comItensDeComanda(itensDeComanda)
                .comListaDePreco(listaDePreco).comNumeroComanda(numeroComanda).construir();
    }

    public Comanda construirComID() throws DadoInvalidoException {
        return new ComandaBuilder().comId(id).comAcrescimo(acrescimo).comCotacao(cotacao).comDesconto(desconto)
                .comDespesaCobranca(despesaCobranca).comFrete(frete).comHistorico(historico).comItensDeComanda(itensDeComanda)
                .comListaDePreco(listaDePreco).comNumeroComanda(numeroComanda).construir();
    }

}
