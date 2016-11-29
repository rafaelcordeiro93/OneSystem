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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMEMITIDO",
        sequenceName = "SEQ_ITEMEMITIDO")
public class ItemEmitido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMEMITIDO")
    private Long id;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    @NotNull(message = "{valor_unitario_not_null}")
    @Max(value = 9999999, message = "{valor_unitario_max}")
    @Min(value = 0, message = "{valor_unitario_min}")
    @Column(nullable = false)
    private BigDecimal valorUnitario = BigDecimal.ZERO;
    @NotNull(message = "{valor_desconto_not_null}")
    @Max(value = 9999999, message = "{valor_desconto_max}")
    @Min(value = 0, message = "{valorDesconto_min}")
    private BigDecimal valorDesconto = BigDecimal.ZERO;
    @NotNull(message = "{valor_acrescimo_not_null}")
    @Max(value = 9999999, message = "{valor_acrescimo_max}")
    @Min(value = 0, message = "{valor_acrescimo_min}")
    private BigDecimal valorAcrescimo = BigDecimal.ZERO;
    @NotNull(message = "{nota_emitida_not_null}")
    @ManyToOne
    private NotaEmitida notaEmitida;
    @OneToMany(mappedBy = "itemEmitido")
    private List<ItemPorDeposito> listaDeItemPorDeposito;

    public ItemEmitido() {
    }

    public ItemEmitido(Long id, Item item, BigDecimal valorUnitario, BigDecimal valorDesconto,
            BigDecimal valorAcrescimo, NotaEmitida notaEmitida) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.notaEmitida = notaEmitida;
        this.valorAcrescimo = valorAcrescimo;
        this.valorDesconto = valorDesconto;
        this.valorUnitario = valorUnitario;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public BigDecimal getValorAcrescimo() {
        return valorAcrescimo;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "notaEmitida", "valorAcrescimo", "valorDesconto", "valorUnitario");
        new ValidadorDeCampos<ItemEmitido>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Conta)) {
            return false;
        }
        ItemEmitido outro = (ItemEmitido) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemEmitido{" + "id=" + id + ", item=" + item + ", valorUnitario=" + valorUnitario + ", valorDesconto=" + valorDesconto + ", valorAcrescimo=" + valorAcrescimo + ", notaEmitida=" + notaEmitida + '}';
    }

}
