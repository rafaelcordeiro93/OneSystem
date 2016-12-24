/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMPORDEPOSITO",
        sequenceName = "SEQ_ITEMPORDEPOSITO")
public class ItemPorDeposito implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_ITEMPORDEPOSITO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{quantidade_not_null}")
    @Max(value = 9999999, message = "{quantidade_max}")
    @Min(value = 0, message = "{quantidade_min}")
    @Column(nullable = false)
    private BigDecimal quantidade = BigDecimal.ZERO;
    @NotNull(message = "{deposito_not_null}")
    @ManyToOne
    private Deposito deposito;
//    @NotNull(message = "{item_emitido_not_null}")
    @ManyToOne
    private ItemEmitido itemEmitido;

    public ItemPorDeposito() {
    }

    public ItemPorDeposito(Long id, BigDecimal quantidade, Deposito deposito, ItemEmitido itemEmitido) throws DadoInvalidoException {
        this.id = id;
        this.quantidade = quantidade;
        this.deposito = deposito;
        this.itemEmitido = itemEmitido;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public ItemEmitido getItemEmitido() {
        return itemEmitido;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("quantidade", "deposito", "itemEmitido");
        new ValidadorDeCampos<ItemPorDeposito>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Deposito)) {
            return false;
        }
        ItemPorDeposito outro = (ItemPorDeposito) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemPorDeposito{" + "id=" + id + ", quantidade=" + quantidade + ", deposito=" + deposito + '}';
    }

}
