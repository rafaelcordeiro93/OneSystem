/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Item;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class SaldoEmContaTemplate {
    
    private ContaDeEstoque contaDeEstoque;
    
    private Item item;
    
    private BigDecimal saldo;

    public SaldoEmContaTemplate(ContaDeEstoque contaDeEstoque, Item item, BigDecimal saldo) {
        this.contaDeEstoque = contaDeEstoque;
        this.item = item;
        this.saldo = saldo;
    }

    public ContaDeEstoque getContaDeEstoque() {
        return contaDeEstoque;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "SaldoPorContaDeEstoqueTemplate{" + "contaDeEstoque=" + contaDeEstoque + ", item=" + item + ", saldo=" + saldo + '}';
    }
    
}
