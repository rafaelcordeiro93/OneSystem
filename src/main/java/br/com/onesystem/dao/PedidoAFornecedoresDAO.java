package br.com.onesystem.dao;

import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.valueobjects.EstadoDePedido;

public class PedidoAFornecedoresDAO extends GenericDAO<PedidoAFornecedores> {

    public PedidoAFornecedoresDAO() {
        super(PedidoAFornecedores.class);
        limpar();
    }

    public PedidoAFornecedoresDAO porId(Long id) {
        if (id != null) {
            where += " and pedidoAFornecedores.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

    public PedidoAFornecedoresDAO porEstado(EstadoDePedido estado) {
        if (estado != null) {
            where += " and pedidoAFornecedores.estado = :bEstado ";
            parametros.put("bEstado", estado);
        }
        return this;
    }
}
