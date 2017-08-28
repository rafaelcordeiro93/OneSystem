/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CondicionalBuilder {

    private Long id;
    private List<ItemDeCondicional> itensDeCondicional;
    private ListaDePreco listaDePreco;
    private Cotacao cotacao;
    private String historico;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;
    private Pessoa pessoa;
    private Operacao operacao;
    private Filial filial;

    public CondicionalBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public CondicionalBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public CondicionalBuilder comItensDeCondicional(List<ItemDeCondicional> itensDeCondicional) {
        this.itensDeCondicional = itensDeCondicional;
        return this;
    }

    public CondicionalBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public CondicionalBuilder comObservacao(String historico) {
        this.historico = historico;
        return this;
    }

    public CondicionalBuilder comFilial(Filial filial){
        this.filial = filial;
        return this;
    }
    
    public CondicionalBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public CondicionalBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public CondicionalBuilder comDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
        return this;
    }

    public CondicionalBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }

    public CondicionalBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public CondicionalBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public Condicional construir() throws DadoInvalidoException {
        return new Condicional(id, pessoa, listaDePreco, cotacao, itensDeCondicional, operacao, historico, desconto, acrescimo, despesaCobranca, frete, filial);
    }

}
