/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemRecebido;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemRecebidoBuilder {

    private Long id;
    private Item item;
    private BigDecimal unitario = BigDecimal.ZERO;
    private NotaRecebida notaRecebida;
    private List<Estoque> estoque;

    public ItemRecebidoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ItemRecebidoBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemRecebidoBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }

    public ItemRecebidoBuilder comNotaRecebida(NotaRecebida notaRecebida) {
        this.notaRecebida = notaRecebida;
        return this;
    }
    
    public ItemRecebidoBuilder comEstoque(List<Estoque> estoque) {
        this.estoque = estoque;
        return this;
    }

    public ItemRecebido construir() throws DadoInvalidoException {
        return new ItemRecebido(id, item, unitario, notaRecebida, estoque);
    }

}
