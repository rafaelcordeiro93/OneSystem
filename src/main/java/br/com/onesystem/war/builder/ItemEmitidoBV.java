/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.builder.ItemEmitidoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemEmitidoBV {

    private Long id;
    private Item item;
    private BigDecimal unitario = BigDecimal.ZERO;
    private NotaEmitida notaEmitida;
    private List<Estoque> estoque = new ArrayList<Estoque>();

    public ItemEmitidoBV() {
    }

    public ItemEmitidoBV(ItemEmitido itemEmitidoSelecionado) {
        this.id = itemEmitidoSelecionado.getId();
        this.item = itemEmitidoSelecionado.getItem();
        this.notaEmitida = itemEmitidoSelecionado.getNotaEmitida();
        this.unitario = itemEmitidoSelecionado.getUnitario();
        estoque = itemEmitidoSelecionado.getEstoque();
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

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
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
        return quantidade;
    }

    public BigDecimal getTotal() {
        return getQuantidade().multiply(unitario == null ? BigDecimal.ZERO : unitario);
    }

    public ItemEmitido construir() throws DadoInvalidoException {
        return new ItemEmitidoBuilder().comItem(item).comNotaEmitida(notaEmitida).comUnitario(unitario).comEstoque(estoque).construir();
    }

    public ItemEmitido construirComId() throws DadoInvalidoException {
        return new ItemEmitidoBuilder().comId(id).comItem(item).comNotaEmitida(notaEmitida).comUnitario(unitario).comEstoque(estoque).construir();
    }

}
