/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDeNotaBuilder {

    private Long id;
    private Item item;
    private BigDecimal unitario = BigDecimal.ZERO;
    private List<QuantidadeDeItemPorDeposito> listaDeQuantidade;
    private BigDecimal iva;
    private SituacaoFiscal situacaoFiscal;
    private Long cfop;
    private BigDecimal valorTotalIva;
    private LoteItem loteItem;

    public ItemDeNotaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemDeNotaBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemDeNotaBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }

    public ItemDeNotaBuilder comIva(BigDecimal iva) {
        this.iva = iva;
        return this;
    }

    public ItemDeNotaBuilder comValorIva(BigDecimal valorTotalIva) {
        this.valorTotalIva = valorTotalIva;
        return this;
    }

    public ItemDeNotaBuilder comCfop(Long cfop) {
        this.cfop = cfop;
        return this;
    }

    public ItemDeNotaBuilder comSituacaoFiscal(SituacaoFiscal situacaoFiscal) {
        this.situacaoFiscal = situacaoFiscal;
        return this;
    }

    public ItemDeNotaBuilder comListaDeQuantidade(List<QuantidadeDeItemPorDeposito> listaDeQuantidade) {
        this.listaDeQuantidade = listaDeQuantidade;
        return this;
    }

    public ItemDeNotaBuilder comLoteItem(LoteItem loteItem) {
        this.loteItem = loteItem;
        return this;
    }

    public ItemDeNota construir() throws DadoInvalidoException {
        return new ItemDeNota(id, item, unitario, listaDeQuantidade, cfop, iva, valorTotalIva, situacaoFiscal, loteItem);
    }

}
