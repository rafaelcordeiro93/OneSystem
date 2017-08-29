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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Cordeiro
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_ITEMDEPEDIDOCANCELADO",
        sequenceName = "SEQ_ITEMDEPEDIDOCANCELADO")
public class ItemDePedidoCancelado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEMDEPEDIDOCANCELADO")
    private Long id;
    @NotNull(message = "{item_not_null}")
    @ManyToOne
    private ItemDePedido itemDePedido;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCancelamento = Calendar.getInstance().getTime();
    @ManyToOne
    private Motivo motivo;
    @ManyToOne
    private Pedido pedido;
    @Column(nullable = false)
    private BigDecimal quantidade;

    public ItemDePedidoCancelado() {
    }

    public ItemDePedidoCancelado(Long id, ItemDePedido itemDePedido, Date dataCancelamento, BigDecimal quantidade, Pedido pedido, Motivo motivo) throws DadoInvalidoException {
        this.id = id;
        this.itemDePedido = itemDePedido;
        this.dataCancelamento = dataCancelamento;
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.motivo = motivo;
        ehValido();
    }

    public void paraPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public ItemDePedido getItemDePedido() {
        return itemDePedido;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("itemDePedido");
        new ValidadorDeCampos<ItemDePedidoCancelado>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ItemDePedidoCancelado)) {
            return false;
        }
        ItemDePedidoCancelado outro = (ItemDePedidoCancelado) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ItemDePedidoCancelado{" + "id=" + id + ", itemDePedido=" + itemDePedido + ", dataCancelamento=" + dataCancelamento + ", motivo=" + motivo + ", pedido=" + pedido + ", quantidade=" + quantidade + '}';
    }

}
