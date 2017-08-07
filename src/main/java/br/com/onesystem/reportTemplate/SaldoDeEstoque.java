/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Deposito;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class SaldoDeEstoque implements Serializable {

    private Long id;
    private Deposito deposito;
    private BigDecimal saldo;

    public SaldoDeEstoque(Long id, Deposito deposito, BigDecimal saldo) {
        this.id = id;
        this.deposito = deposito;
        this.saldo = saldo;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof SaldoDeEstoque)) {
            return false;
        }
        SaldoDeEstoque outro = (SaldoDeEstoque) objeto;
        if (this.deposito.getId() == null) {
            return false;
        }
        return this.deposito.getId().equals(outro.deposito.getId());
    }

    @Override
    public String toString() {
        return "SaldoDeEstoque{" + "id=" + id + ", deposito=" + deposito + ", saldo=" + saldo + '}';
    }

}
