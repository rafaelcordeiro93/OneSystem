/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemRecebido;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.domain.builder.ItemRecebidoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemRecebidoBV {

    private Long id;
    private Item item;
    private BigDecimal unitario;
    private NotaRecebida notaRecebida;
    private List<Estoque> estoque = new ArrayList<Estoque>();

    public ItemRecebidoBV() {
    }

    public ItemRecebidoBV(ItemRecebido itemEmitidoSelecionado) {
        this.id = itemEmitidoSelecionado.getId();
        this.item = itemEmitidoSelecionado.getItem();
        this.notaRecebida = itemEmitidoSelecionado.getNotaRecebida();
        this.unitario = itemEmitidoSelecionado.getUnitario();
        estoque = itemEmitidoSelecionado.getEstoques();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public NotaRecebida getNotaRecebida() {
        return notaRecebida;
    }

    public void setNotaRecebida(NotaRecebida notaRecebida) {
        this.notaRecebida = notaRecebida;
    }

    public List<Estoque> getEstoque() {
        return estoque;
    }

    public void setEstoque(List<Estoque> estoque) {
        this.estoque = estoque;
    }

    public BigDecimal getQuantidade() {
        BigDecimal quantidade = BigDecimal.ZERO;
        for (Estoque e : estoque) {
            quantidade = quantidade.add(e.getQuantidade());
        }
        return quantidade.equals(BigDecimal.ZERO) ? null : quantidade;
    }

    public BigDecimal getTotal() {
        
        return getQuantidade() == null ? BigDecimal.ZERO: getQuantidade().multiply(unitario == null ? BigDecimal.ZERO : unitario);
    }

    public ItemRecebido construir() throws DadoInvalidoException {
        return new ItemRecebidoBuilder().comItem(item).comNotaRecebida(notaRecebida).comUnitario(unitario).comEstoque(estoque).construir();
    }

    public ItemRecebido construirComId() throws DadoInvalidoException {
        return new ItemRecebidoBuilder().comId(id).comItem(item).comNotaRecebida(notaRecebida).comUnitario(unitario).comEstoque(estoque).construir();
    }

}
