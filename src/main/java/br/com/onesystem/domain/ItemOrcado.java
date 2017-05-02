/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFomatter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;
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
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMORCADO",
        sequenceName = "SEQ_ITEMORCADO")
public class ItemOrcado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMORCADO")
    private Long id;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    @NotNull(message = "{valor_unitario_not_null}")
    @Max(value = 9999999, message = "{valor_unitario_max}")
    @Min(value = 0, message = "{valor_unitario_min}")
    @Column(nullable = false)
    private BigDecimal unitario = BigDecimal.ZERO;
    @ManyToOne
    private Orcamento orcamento;
    @NotNull(message = "{quantidade_not_null}")
    @Min(value = 0, message = "{quantidade_min}")
    @Column(nullable = false)
    private BigDecimal quantidade;

    public ItemOrcado() {
    }

    public ItemOrcado(Long id, Item item, BigDecimal valorUnitario, BigDecimal quantidade) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.unitario = valorUnitario;
        this.quantidade = quantidade;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getUnitario() {
        return unitario;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getTotal() {
        return getQuantidade().multiply(unitario);
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public String getTotalFormatado() {
        if (orcamento != null) {
            return MoedaFomatter.format(orcamento.getCotacao().getConta().getMoeda(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }

    public String getUnitarioFormatado() {
        if (orcamento != null) {
            return MoedaFomatter.format(orcamento.getCotacao().getConta().getMoeda(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }

    public void preparaInclusao(Orcamento orcamento) {
        if (this.orcamento == null) {
            this.id = null;
            this.orcamento = orcamento;
        }
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "unitario", "quantidade");
        new ValidadorDeCampos<ItemOrcado>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ItemOrcado)) {
            return false;
        }
        ItemOrcado outro = (ItemOrcado) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemOrcado{" + "id=" + id + ", item=" + (item != null ? item.getId() : null) + ", unitario=" + unitario + ", orcamento=" + (orcamento != null ? orcamento.getId() : null) + '}';
    }

}
