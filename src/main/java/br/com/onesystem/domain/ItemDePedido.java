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
 * @author Rafael Cordeiro
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMDEPEDIDO",
        sequenceName = "SEQ_ITEMDEPEDIDO")
public class ItemDePedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMDEPEDIDO")
    private Long id;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private Item item;
    @NotNull(message = "{valor_unitario_not_null}")
    @Max(value = 9999999, message = "{valor_unitario_max}")
    @Min(value = 0, message = "{valor_unitario_min}")
    @Column(nullable = false)
    private BigDecimal valorUnitario = BigDecimal.ZERO;
    @ManyToOne
    private Pedido pedido;
    @Column(nullable = false)
    private BigDecimal quantidade;

    public ItemDePedido() {
    }

    public ItemDePedido(Long id, Item item, BigDecimal valorUnitario, BigDecimal quantidade, Pedido pedido) throws DadoInvalidoException {
        this.id = id;
        this.item = item;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.pedido = pedido;
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getTotal() {
        return getQuantidade().multiply(valorUnitario);
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

//    public String getTotalFormatado() {
//        if (pedido != null) {
//            return MoedaFormatter.format(pedido.getMoedaPadrao(), getTotal());
//        } else {
//            return NumberFormat.getNumberInstance().format(getTotal());
//        }
//    }
//
//    public String getUnitarioFormatado() {
//        if (pedido != null) {
//            return MoedaFormatter.format(pedido.getMoedaPadrao(), getUnitario());
//        } else {
//            return NumberFormat.getNumberInstance().format(getUnitario());
//        }
//    }
//    public void preparaInclusao(PedidoEmitida pedido) {
//        if (this.pedido == null) {
//            this.id = null;
//            this.pedido = pedido;
//        }
//    }
    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("item", "valorUnitario");
        new ValidadorDeCampos<ItemDePedido>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ItemDePedido)) {
            return false;
        }
        ItemDePedido outro = (ItemDePedido) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemDePedido{" + "id=" + id + ", item=" + item + ", unitario=" + valorUnitario + ", pedido=" + pedido + ", quantidade=" + quantidade + '}';
    }

}
