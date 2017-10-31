/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFormatter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMDECONDICIONAL",
        sequenceName = "SEQ_ITEMDECONDICIONAL")
public class ItemDeCondicional implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMDECONDICIONAL")
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
    private Condicional condicional;
    @NotNull(message = "{quantidade_not_null}")
    @Min(value = 0, message = "{quantidade_min}")
    @Column(nullable = false)
    private BigDecimal quantidade;
    @OneToMany(mappedBy = "itemDeCondicional", cascade = CascadeType.ALL)
    private List<Estoque> estoques = new ArrayList();
    @ManyToOne
    private LoteItem loteItem;

    public ItemDeCondicional() {
    }

    public ItemDeCondicional(Long id, Item item, BigDecimal valorUnitario, BigDecimal quantidade, LoteItem loteItem) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.unitario = valorUnitario;
        this.quantidade = quantidade;
        this.loteItem = loteItem;
        ehValido();
    }

    public LoteItem getLoteItem() {
        return loteItem;
    }
    
    public Long getId() {
        return id;
    }
    
    public void adicionaEstoque(Estoque estoque){
        this.estoques.add(estoque);
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getUnitario() {
        return unitario;
    }

    public Condicional getCondicional() {
        return condicional;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public List<Estoque> getEstoques() {
        return estoques;
    }
    
    public BigDecimal getTotal() {
        return getQuantidade().multiply(unitario);
    }

    public void paraCondicional(Condicional condicional) throws DadoInvalidoException {
        this.condicional = condicional;
    }

    public String getTotalFormatado() {
        if (condicional != null) {
            return MoedaFormatter.format(condicional.getCotacao().getConta().getMoeda(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }

    public String getUnitarioFormatado() {
        if (condicional != null) {
            return MoedaFormatter.format(condicional.getCotacao().getConta().getMoeda(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "unitario", "quantidade");
        new ValidadorDeCampos<ItemDeCondicional>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ItemDeCondicional)) {
            return false;
        }
        ItemDeCondicional outro = (ItemDeCondicional) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemDeCondicional{" + "id=" + id + ", item=" + (item != null ? item.getId() : null) + ", unitario=" + unitario + ", condicional=" + (condicional != null ? condicional.getId() : null) + '}';
    }

}
