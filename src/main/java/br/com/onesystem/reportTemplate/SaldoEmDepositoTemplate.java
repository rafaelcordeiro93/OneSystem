/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class SaldoEmDepositoTemplate {
    
    private Deposito deposito;
    
    private Item item;
    
    private BigDecimal saldo;

    public SaldoEmDepositoTemplate(Deposito deposito, Item item, BigDecimal saldo) {
        this.deposito = deposito;
        this.item = item;
        this.saldo = saldo;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "SaldoPorDepositoTemplate{" + "deposito=" + deposito + ", item=" + item + ", saldo=" + saldo + '}';
    }
    
}
