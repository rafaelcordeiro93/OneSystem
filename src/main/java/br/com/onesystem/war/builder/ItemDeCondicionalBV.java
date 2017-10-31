/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.builder.ItemDeCondicionalBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MoedaFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDeCondicionalBV {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;
    private Condicional condicional;
    private BigDecimal aFaturar = BigDecimal.ZERO;
    private BigDecimal saldo;
    private LoteItem loteItem;

    public ItemDeCondicionalBV() {
    }

    public ItemDeCondicionalBV(ItemDeCondicional item) {
        this.id = item.getId();
        this.item = item.getItem();
        this.unitario = item.getUnitario();
        this.quantidade = item.getQuantidade();
        this.condicional = item.getCondicional();
        this.loteItem = item.getLoteItem();
    }

    public Long getId() {
        return id;
    }

    public ItemDeCondicionalBV setId(Long id) {
        this.id = id;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getUnitario() {
        return unitario;
    }

    public void setUnitario(BigDecimal unitario) {
        this.unitario = unitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getAFaturar() {
        return aFaturar;
    }

    public void setAFaturar(BigDecimal aFaturar) {
        this.aFaturar = aFaturar;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public int getComparaSaldo() {
        return saldo.compareTo(aFaturar);
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getTotalAFaturarFormatado() {
        return MoedaFormatter.format(condicional.getCotacao().getConta().getMoeda(), getUnitario().multiply(aFaturar));
    }

    public String getTotalFormatado() {
        if (condicional != null) {
            return MoedaFormatter.format(condicional.getCotacao().getConta().getMoeda(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }

    public String getUnitarioFormatado() {
        if (condicional != null) {
            return MoedaFormatter.format(condicional.getCotacao().getConta().getMoeda(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }

    public Condicional getCondicional() {
        return condicional;
    }

    public void setCondicional(Condicional comanda) {
        this.condicional = comanda;
    }

    public LoteItem getLoteItem() {
        return loteItem;
    }

    public void setLoteItem(LoteItem loteItem) {
        this.loteItem = loteItem;
    }

    public BigDecimal getTotal() {
        return getQuantidade() == null ? BigDecimal.ZERO : getQuantidade().multiply(unitario == null ? BigDecimal.ZERO : unitario);
    }

    public ItemDeCondicional construir() throws DadoInvalidoException {
        return new ItemDeCondicionalBuilder().comItem(item).comUnitario(unitario).comQuantidade(quantidade).comLote(loteItem).construir();
    }

    public ItemDeCondicional construirComId() throws DadoInvalidoException {
        return new ItemDeCondicionalBuilder().comId(id).comItem(item).comUnitario(unitario).comQuantidade(quantidade).comLote(loteItem).construir();
    }

}
