/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemOrcado;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.builder.ItemOrcadoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MoedaFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemOrcadoBV {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private BigDecimal quantidade;
    private List<QuantidadeDeItemPorDeposito> quantidadePorDeposito = new ArrayList<>();
    private Orcamento orcamento;
    private BigDecimal faturar;

    public ItemOrcadoBV() {
    }

    public ItemOrcadoBV(ItemOrcado item) {
        this.id = item.getId();
        this.item = item.getItem();
        this.unitario = item.getUnitario();
        this.quantidade = item.getQuantidade();
        this.orcamento = item.getOrcamento();
    }

    public Long getId() {
        return id;
    }

    public ItemOrcadoBV setId(Long id) {
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

    public BigDecimal getFaturar() {
        return faturar;
    }

    public void setFaturar(BigDecimal faturar) {
        this.faturar = faturar;
    }

    public String getTotalAFaturarFormatado() {
        return MoedaFormatter.format(orcamento.getCotacao().getConta().getMoeda(), getUnitario().multiply(faturar));
    }

    public String getTotalFormatado() {
        if (orcamento != null) {
            return MoedaFormatter.format(orcamento.getCotacao().getConta().getMoeda(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }

    public String getUnitarioFormatado() {
        if (orcamento != null) {
            return MoedaFormatter.format(orcamento.getCotacao().getConta().getMoeda(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }

    public List<QuantidadeDeItemPorDeposito> getQuantidadePorDeposito() {
        return quantidadePorDeposito;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public void setQuantidadePorDeposito(List<QuantidadeDeItemPorDeposito> quantidadePorDeposito) {
        this.quantidadePorDeposito = quantidadePorDeposito;
    }

    public int getQuantidadeDeFaturamento() {
        BigDecimal b = BigDecimal.ZERO;
        for (QuantidadeDeItemPorDeposito q : quantidadePorDeposito) {
            b = b.add(q.getQuantidade());
        }
        return faturar.compareTo(b);
    }

    public BigDecimal getQuantidadeAFaturar() {
        BigDecimal b = BigDecimal.ZERO;
        for (QuantidadeDeItemPorDeposito q : quantidadePorDeposito) {
            b = b.add(q.getQuantidade());
        }
        return b;
    }

    public BigDecimal getTotal() {
        return getFaturar() == null ? BigDecimal.ZERO : getFaturar().multiply(unitario == null ? BigDecimal.ZERO : unitario);
    }

    public ItemOrcado construir() throws DadoInvalidoException {
        return new ItemOrcadoBuilder().comItem(item).comUnitario(unitario).comQuantidade(quantidade).construir();
    }

    public ItemOrcado construirComId() throws DadoInvalidoException {
        return new ItemOrcadoBuilder().comId(id).comItem(item).comUnitario(unitario).comQuantidade(quantidade).construir();
    }

}
