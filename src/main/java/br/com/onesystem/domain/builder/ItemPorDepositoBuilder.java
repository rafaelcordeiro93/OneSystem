/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ItemPorDeposito;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemPorDepositoBuilder {

    private Long id;
    private ItemEmitido itemEmitido;
    private BigDecimal quantidade = BigDecimal.ZERO;
    private Deposito deposito;

    public ItemPorDepositoBuilder comID(Long id) {
        this.id = id;
        return this;
    }

    public ItemPorDepositoBuilder comItemEmitido(ItemEmitido itemEmitido) {
        this.itemEmitido = itemEmitido;
        return this;
    }

    public ItemPorDepositoBuilder comQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public ItemPorDepositoBuilder comDeposito(Deposito deposito) {
        this.deposito = deposito;
        return this;
    }

    public ItemPorDeposito construir() throws DadoInvalidoException {
        return new ItemPorDeposito(id, quantidade, deposito, itemEmitido);
    }

}
