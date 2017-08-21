/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.CondicionalBuilder;
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
public class CondicionalBV implements Serializable, BuilderView<Condicional> {

    private Long id;
    private List<ItemDeCondicional> itensDeCondicional = new ArrayList<>();
    private ListaDePreco listaDePreco;
    private Cotacao cotacao;
    private String observacao;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;
    private BigDecimal porcentagemAcrescimo;
    private BigDecimal porcentagemDesconto;
    private Pessoa pessoa;
    private Operacao operacao;

    public CondicionalBV() {
    }

    public CondicionalBV(Condicional c) {
        this.id = c.getId();
        this.listaDePreco = c.getListaDePreco();
        this.cotacao = c.getCotacao();
        this.observacao = c.getObservacao();
        this.acrescimo = c.getAcrescimo();
        this.desconto = c.getDesconto();
        this.despesaCobranca = c.getDespesaCobranca();
        this.frete = c.getFrete();
        this.pessoa = c.getPessoa();
        this.operacao = c.getOperacao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemDeCondicional> getItensDeCondicional() {
        return itensDeCondicional;
    }

    public void setItensDeCondicional(List<ItemDeCondicional> itensDeCondicional) {
        this.itensDeCondicional = itensDeCondicional;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public Condicional construir() throws DadoInvalidoException {
        return new CondicionalBuilder().comAcrescimo(acrescimo).comCotacao(cotacao).comDesconto(desconto)
                .comDespesaCobranca(despesaCobranca).comFrete(frete).comObservacao(observacao).comItensDeCondicional(itensDeCondicional)
                .comListaDePreco(listaDePreco).comPessoa(pessoa).comOperacao(operacao).construir();
    }

    public Condicional construirComID() throws DadoInvalidoException {
        return new CondicionalBuilder().comId(id).comAcrescimo(acrescimo).comCotacao(cotacao).comDesconto(desconto)
                .comDespesaCobranca(despesaCobranca).comFrete(frete).comObservacao(observacao).comItensDeCondicional(itensDeCondicional)
                .comListaDePreco(listaDePreco).comPessoa(pessoa).comOperacao(operacao).construir();
    }

}
