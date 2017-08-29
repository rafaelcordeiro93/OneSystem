/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.dao.ItemDePedidoCanceladoDAO;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ItemDePedidoCancelado;
import br.com.onesystem.domain.Pedido;
import br.com.onesystem.domain.builder.ItemDePedidoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MoedaFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDePedidoBV {

    private Long id;
    private Item item;
    private BigDecimal valorUnitario;
    private BigDecimal quantidade;
    private Pedido pedido;
    private List<QuantidadeDeItemPorDeposito> quantidadePorDeposito = new ArrayList<>();
    private List<QuantidadeDeItemPorDeposito> listaDeQuantidade = new ArrayList<QuantidadeDeItemPorDeposito>();

    public ItemDePedidoBV() {
    }

    public ItemDePedidoBV(ItemDePedido item) {
        this.id = item.getId();
        this.item = item.getItem();
        this.valorUnitario = item.getValorUnitario();
        this.quantidade = item.getQuantidade();
        this.pedido = item.getPedido();
    }

    public Long getId() {
        return id;
    }

    public ItemDePedidoBV setId(Long id) {
        this.id = id;
        return this;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<QuantidadeDeItemPorDeposito> getListaDeQuantidade() {
        return listaDeQuantidade;
    }

    public void setListaDeQuantidade(List<QuantidadeDeItemPorDeposito> listaDeQuantidade) {
        this.listaDeQuantidade = listaDeQuantidade;
    }

    public String getTotalFormatado() {
        if (pedido != null) {
            return MoedaFormatter.format(pedido.getMoedaPadrao(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }

    public String getUnitarioFormatado() {
        if (pedido != null) {
            return MoedaFormatter.format(pedido.getMoedaPadrao(), getValorUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getValorUnitario());
        }
    }

    public BigDecimal getTotalItemCancelado() {
        try {
            ItemDePedidoCancelado i = new ItemDePedidoCanceladoDAO().porItemDePedido(this.construirComId()).resultado();
            if (i != null) {
                return i.getQuantidade();
            } else {
                return BigDecimal.ZERO;
            }
        } catch (DadoInvalidoException die) {
            die.print();
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getTotalListaSaldoDeQuantidade() {
        return getQuantidade().subtract(getListaDeQuantidade().stream().map(q -> q.getSaldoDeEstoque().getSaldo()).reduce(BigDecimal.ZERO, BigDecimal::add)).negate();
    }

    public boolean habilitaSelecaoDeDepoistos() {
        if (this.quantidade.compareTo(getTotalListaSaldoDeQuantidade().add(getTotalItemCancelado())) < 0) {
            return true;
        } else {
            return false;
        }
    }

    public BigDecimal getTotalListaDeQuantidade() {
        return getListaDeQuantidade().stream().map(QuantidadeDeItemPorDeposito::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getValorTotalListaDeQuantidadeFormatado() {
        BigDecimal resultado = getValorUnitario().multiply(getListaDeQuantidade().stream().map(QuantidadeDeItemPorDeposito::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add));
        return MoedaFormatter.format(pedido.getMoedaPadrao(), resultado);
    }

    public int getComparaQuantidadeDevolucao() {
        BigDecimal r = getQuantidade().subtract(getTotalListaSaldoDeQuantidade());
        return r.compareTo(getTotalListaDeQuantidade());
    }

    public BigDecimal getTotal() {
        return getQuantidade() == null ? BigDecimal.ZERO : getQuantidade().multiply(valorUnitario == null ? BigDecimal.ZERO : valorUnitario);
    }

    public ItemDePedido construir() throws DadoInvalidoException {
        return new ItemDePedidoBuilder().comItem(item).comUnitario(valorUnitario).comQuantidade(quantidade).construir();
    }

    public ItemDePedido construirComId() throws DadoInvalidoException {
        return new ItemDePedidoBuilder().comId(id).comItem(item).comUnitario(valorUnitario).comQuantidade(quantidade).construir();
    }

}
