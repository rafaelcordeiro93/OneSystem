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
@SequenceGenerator(name = "SEQ_SALDODELOTEPORDEPOSITO", initialValue = 1,
        allocationSize = 1, sequenceName = "SEQ_SALDODELOTEPORDEPOSITO")
public class SaldoDeLotePorDeposito implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_SALDODELOTEPORDEPOSITO", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{deposito_not_null}")
    @ManyToOne
    private Deposito deposito;

    @NotNull(message = "{lote_item_not_null}")
    @ManyToOne
    private LoteItem loteItem;

    @Column(insertable = false, updatable = false)
    private BigDecimal saldo;

    public SaldoDeLotePorDeposito() {
    }

    public Long getId() {
        return id;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public LoteItem getLoteItem() {
        return loteItem;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "SaldoDeLotePorDeposito{" + "id=" + id + ", deposito=" + deposito + ", loteItem=" + loteItem + ", saldo=" + saldo + '}';
    }

}
