/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ParcelaDePedido;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDePedido;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Cordeiro
 */
public class PedidoAFornecedoresBuilder {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private FormaDeRecebimento formaDeRecebimento;
    private Date previsaoDeEntrega;
    private Date emissao;
    private String contato;
    private String observacao;
    private EstadoDePedido estado;
    private Moeda moedaPadrao;
    private List<ItemDePedido> itens;
    private List<ParcelaDePedido> parcelaDePedido;
    private BigDecimal desconto;
    private BigDecimal acrescimo = BigDecimal.ZERO;
    private BigDecimal despesaCobranca = BigDecimal.ZERO;
    private BigDecimal frete = BigDecimal.ZERO;
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;

    public PedidoAFornecedoresBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public PedidoAFornecedoresBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public PedidoAFornecedoresBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public PedidoAFornecedoresBuilder comItens(List<ItemDePedido> itens) throws DadoInvalidoException {
//        if (id == null) {
//            List<ItemDePedido> itensCol = itens.stream().collect(Collectors.toList());
//            if (itensCol != null && !itensCol.isEmpty()) {
//                for (ItemDePedido i : itensCol) {
//                    itensCol.set(itensCol.indexOf(i), new ItemDePedidoBV(i).construir());
//                }
//            } else {
//                throw new EDadoInvalidoException(new BundleUtil().getMessage("Itens_Devem_Ser_Informados"));
//            }
//            this.itens = itensCol;
//        } else {
//            this.itens = itens;
//        }
        this.itens = itens;
        return this;
    }

    public PedidoAFornecedoresBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public PedidoAFornecedoresBuilder comPrevisaoDeEntrega(Date previsaoDeEntrega) {
        this.previsaoDeEntrega = previsaoDeEntrega;
        return this;
    }

    public PedidoAFornecedoresBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public PedidoAFornecedoresBuilder comMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
        return this;
    }

    public PedidoAFornecedoresBuilder comEstado(EstadoDePedido estado) {
        this.estado = estado;
        return this;
    }

    public PedidoAFornecedoresBuilder comContato(String contato) {
        this.contato = contato;
        return this;
    }

    public PedidoAFornecedoresBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public PedidoAFornecedoresBuilder comParcelasDePedidos(List<ParcelaDePedido> parcelaDePedido) {
        this.parcelaDePedido = parcelaDePedido;
        return this;
    }

    public PedidoAFornecedoresBuilder comTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
        return this;
    }

    public PedidoAFornecedoresBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public PedidoAFornecedoresBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public PedidoAFornecedoresBuilder comDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
        return this;
    }

    public PedidoAFornecedoresBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }

    public PedidoAFornecedores construir() throws DadoInvalidoException {
        return new PedidoAFornecedores(id, pessoa, operacao, itens, contato, estado, formaDeRecebimento, parcelaDePedido,
                moedaPadrao, desconto, acrescimo, despesaCobranca, frete, totalEmDinheiro, emissao, previsaoDeEntrega, observacao);
    }

}
