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
import org.hibernate.annotations.Immutable;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(name = "SEQ_SALDODEESTOQUE", initialValue = 1,
        allocationSize = 1, sequenceName = "SEQ_SALDODEESTOQUE")
@Immutable
public class SaldoDeEstoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_SALDODEESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{deposito_not_null}")
    @ManyToOne
    private Deposito deposito;

    @NotNull(message = "{conta_de_estoque_not_null}")
    @ManyToOne
    private ContaDeEstoque contaDeEstoque;

    @ManyToOne(optional = true)
    private LoteItem loteItem;

    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    
    @NotNull(message = "{filial_not_null}")
    @ManyToOne
    private Filial filial;

    @Column(insertable = false, updatable = false)
    private BigDecimal saldo;

    public SaldoDeEstoque() {
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

    public ContaDeEstoque getContaDeEstoque() {
        return contaDeEstoque;
    }

    public LoteItem getLoteItem() {
        return loteItem;
    }

    public Filial getFilial() {
        return filial;
    }
    
    @Override
    public String toString() {
        return "SaldoDeEstoquePorDeposito{" + "id=" + id + ", deposito=" + deposito + ", contaDeEstoque=" + contaDeEstoque + ", item=" + item + ", saldo=" + saldo + '}';
    }
}
