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
@SequenceGenerator(name = "SEQ_SALDODEESTOQUEPORCONTA", initialValue = 1,
        allocationSize = 1, sequenceName = "SEQ_SALDODEESTOQUEPORCONTA")
public class SaldoDeEstoquePorConta implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_SALDODEESTOQUEPORCONTA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{contaDeEstoque_not_null}")
    @ManyToOne
    private ContaDeEstoque contaDeEstoque;

    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;

    @Column(insertable = false, updatable = false)
    private BigDecimal saldo;

    public SaldoDeEstoquePorConta() {
    }

    public Long getId() {
        return id;
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
        return "SaldoDeEstoquePorConta{" + "id=" + id + ", contaDeEstoque=" + contaDeEstoque + ", item=" + item + ", saldo=" + saldo + '}';
    }

}
