/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class OrcamentoBuilder {

    private Long id;
    private Pessoa pessoa;
    private List<ItemOrcado> itensOrcados;
    private ListaDePreco listaDePreco;
    private FormaDeRecebimento formaDeRecebimento;
    private Cotacao cotacao;
    private Date vencimento;
    private String historico;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;

    public OrcamentoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public OrcamentoBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public OrcamentoBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public OrcamentoBuilder comItensOrcados(List<ItemOrcado> itensOrcados) {
        this.itensOrcados = itensOrcados;
        return this;
    }

    public OrcamentoBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public OrcamentoBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public OrcamentoBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public OrcamentoBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public OrcamentoBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public OrcamentoBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public OrcamentoBuilder comDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
        return this;
    }

    public OrcamentoBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }

    public Orcamento construir() throws DadoInvalidoException {
        return new Orcamento(id, pessoa, formaDeRecebimento, listaDePreco, cotacao, itensOrcados, vencimento, historico, desconto, acrescimo, despesaCobranca, frete);
    }

}
