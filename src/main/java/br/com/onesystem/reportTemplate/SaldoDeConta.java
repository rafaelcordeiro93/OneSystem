/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Conta;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class SaldoDeConta {
    
    private Conta conta;
    private BigDecimal saldo;

    public SaldoDeConta(Conta conta, BigDecimal saldo) {
        this.conta = conta;
        this.saldo = saldo;
    }

    public Conta getConta() {
        return conta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }    
}
