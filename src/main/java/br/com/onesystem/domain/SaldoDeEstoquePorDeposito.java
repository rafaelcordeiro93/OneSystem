/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(name = "SEQ_SALDODEESTOQUEPORDEPOSITO", initialValue = 1,
        allocationSize = 1, sequenceName = "SEQ_SALDODEESTOQUEPORDEPOSITO")
public class SaldoDeEstoquePorDeposito implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_SALDODEESTOQUEPORDEPOSITO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{deposito_not_null}")
    @ManyToOne
    private Deposito deposito;

    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;

    @Column(insertable = false, updatable = false)
    private BigDecimal saldo;

    public SaldoDeEstoquePorDeposito() {
    }

    public Long getId() {
        return id;
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
        return "SaldoDeEstoquePorDeposito{" + "id=" + id + ", deposito=" + deposito + ", item=" + item + ", saldo=" + saldo + '}';
    }

}
