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
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMRECEBIDO",
        sequenceName = "SEQ_ITEMRECEBIDO")
public class ItemRecebido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMRECEBIDO")
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
    private NotaRecebida notaRecebida;
    @OneToMany(mappedBy = "itemRecebido", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Estoque> estoques;

    public ItemRecebido() {
    }

    public ItemRecebido(Long id, Item item, BigDecimal valorUnitario, NotaRecebida notaRecebida, List<Estoque> estoques) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.notaRecebida = notaRecebida;
        this.unitario = valorUnitario;
        this.estoques = estoques;
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

    public NotaRecebida getNotaRecebida() {
        return notaRecebida;
    }

    public BigDecimal getQuantidade() {
        BigDecimal quantidade = BigDecimal.ZERO;
        for (Estoque e : estoques) {
            quantidade = quantidade.add(e.getQuantidade());
        }
        return quantidade;
    }
    
    public BigDecimal getTotal() {
        return getQuantidade().multiply(unitario);
    }

    public String getTotalFormatado() {
        if (notaRecebida != null) {
            return MoedaFomatter.format(notaRecebida.getMoedaPadrao(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }
    
    public String getUnitarioFormatado(){
        if (notaRecebida != null) {
            return MoedaFomatter.format(notaRecebida.getMoedaPadrao(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }

    public List<Estoque> getEstoques() {
        return estoques;
    }

    public void setEstoques(List<Estoque> estoques) {
        this.estoques = estoques;
    }

    public void preparaInclusao(NotaRecebida notaRecebida) {
        if (this.notaRecebida == null) {
            this.id = null;
            this.notaRecebida = notaRecebida;
        }
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "notaRecebida", "unitario");
        new ValidadorDeCampos<ItemRecebido>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ItemRecebido)) {
            return false;
        }
        ItemRecebido outro = (ItemRecebido) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemEmitido{" + "id=" + id + ", item=" + (item != null ? item.getId() : null) + ", unitario=" + unitario + ", notaRecebida=" + (notaRecebida != null ? notaRecebida.getId() : null) + ", estoques=" + estoques + '}';
    }

}
