/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ItemPorDeposito;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemPorDepositoBV {

    private Long id;
    private ItemEmitido itemEmitido;
    private BigDecimal quantidade = BigDecimal.ZERO;
    private Deposito deposito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemEmitido getItemEmitido() {
        return itemEmitido;
    }

    public void setItemEmitido(ItemEmitido itemEmitido) {
        this.itemEmitido = itemEmitido;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

    public ItemPorDeposito construir() throws DadoInvalidoException {
        return new ItemPorDeposito(null, quantidade, deposito, itemEmitido);
    }

    public ItemPorDeposito construirComId() throws DadoInvalidoException {
        return new ItemPorDeposito(id, quantidade, deposito, itemEmitido);
    }

}
