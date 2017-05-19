/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeComanda;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.builder.ItemDeComandaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MoedaFomatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDeComandaBV {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;
    private List<QuantidadeDeItemPorDeposito> quantidadePorDeposito = new ArrayList<>();
    private Comanda comanda;

    public ItemDeComandaBV() {
    }

    public ItemDeComandaBV(ItemDeComanda item) {
        this.id = item.getId();
        this.item = item.getItem();
        this.unitario = item.getUnitario();
        this.quantidade = item.getQuantidade();
        this.comanda = item.getComanda();
    }

    public Long getId() {
        return id;
    }

    public ItemDeComandaBV setId(Long id) {
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

    public String getTotalFormatado() {
        if (comanda != null) {
            return MoedaFomatter.format(comanda.getCotacao().getConta().getMoeda(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }

    public String getUnitarioFormatado() {
        if (comanda != null) {
            return MoedaFomatter.format(comanda.getCotacao().getConta().getMoeda(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }

    public List<QuantidadeDeItemPorDeposito> getQuantidadePorDeposito() {
        return quantidadePorDeposito;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public BigDecimal getTotal() {
        return getQuantidade() == null ? BigDecimal.ZERO : getQuantidade().multiply(unitario == null ? BigDecimal.ZERO : unitario);
    }

    public ItemDeComanda construir() throws DadoInvalidoException {
        return new ItemDeComandaBuilder().comItem(item).comUnitario(unitario).comQuantidade(quantidade).construir();
    }

    public ItemDeComanda construirComId() throws DadoInvalidoException {
        return new ItemDeComandaBuilder().comId(id).comItem(item).comUnitario(unitario).comQuantidade(quantidade).construir();
    }

}
