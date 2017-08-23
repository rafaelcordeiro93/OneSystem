/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ParcelaDePedido;
import br.com.onesystem.domain.Pedido;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Cordeiro
 */
public class ParcelaDePedidoBuilder {

    private Long id;
    private BigDecimal valor;
    private Date vencimento;
    private Pedido pedido;

    public ParcelaDePedidoBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ParcelaDePedidoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ParcelaDePedidoBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public ParcelaDePedidoBuilder comPedido(Pedido pedido) {
        this.pedido = pedido;
        return this;
    }

    public ParcelaDePedido construir() throws DadoInvalidoException {
        return new ParcelaDePedido(id, valor, vencimento, pedido);
    }

}
