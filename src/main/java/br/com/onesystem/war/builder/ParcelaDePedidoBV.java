/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDePedido;
import br.com.onesystem.domain.ParcelaDePedido;
import br.com.onesystem.domain.Pedido;
import br.com.onesystem.domain.builder.ItemDePedidoBuilder;
import br.com.onesystem.domain.builder.ParcelaDePedidoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ParcelaDePedidoBV {

    private Long id;
    private BigDecimal valor;
    private Date vencimento;
    private Pedido pedido;

    public ParcelaDePedidoBV() {
    }

    public ParcelaDePedidoBV(ParcelaDePedido parcela) {
        this.id = parcela.getId();
        this.valor = parcela.getValor();
        this.pedido = parcela.getPedido();
        this.pedido = parcela.getPedido();
    }

    public Long getId() {
        return id;
    }

    public ParcelaDePedidoBV setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }


  
    public ParcelaDePedido construir() throws DadoInvalidoException {
        return new ParcelaDePedidoBuilder().comValor(valor).comVencimento(vencimento).construir();
    }

    public ParcelaDePedido construirComId() throws DadoInvalidoException {
        return new ParcelaDePedidoBuilder().comId(id).comValor(valor).comVencimento(vencimento).construir();
    }

}
