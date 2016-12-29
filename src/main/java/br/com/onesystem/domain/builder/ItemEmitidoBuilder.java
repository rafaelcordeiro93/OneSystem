/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ItemPorDeposito;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemEmitidoBuilder {
    
    private Long id;
    private Item item;
    private BigDecimal unitario = BigDecimal.ZERO;    
    private NotaEmitida notaEmitida;
    private List<ItemPorDeposito> listaDeItemPorDeposito;
    
    public ItemEmitidoBuilder comId(Long id) {
        this.id = id;
        return this;
    }
    
    public ItemEmitidoBuilder comItem(Item item) {
        this.item = item;
        return this;
    }
    
    public ItemEmitidoBuilder comUnitario(BigDecimal unitario) {
        this.unitario = unitario;
        return this;
    }
    
    public ItemEmitidoBuilder comNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
        return this;
    }
    
    public ItemEmitidoBuilder comItemPorDeposito(List<ItemPorDeposito> listaDeItemPorDeposito) {
        this.listaDeItemPorDeposito = listaDeItemPorDeposito;
        return this;
    }
    
    public ItemEmitido construir() throws DadoInvalidoException {
        return new ItemEmitido(id, item, unitario, notaEmitida, listaDeItemPorDeposito);
    }
    
}
