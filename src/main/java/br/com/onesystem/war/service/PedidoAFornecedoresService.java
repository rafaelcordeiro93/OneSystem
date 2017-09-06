package br.com.onesystem.war.service;

import br.com.onesystem.dao.PedidoAFornecedoresDAO;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.valueobjects.EstadoDePedido;
import java.io.Serializable;
import java.util.List;

public class PedidoAFornecedoresService implements Serializable {

    public List<PedidoAFornecedores> buscarPedidosAFornecedores() {
        return new PedidoAFornecedoresDAO().listaDeResultados();
    }

    public List<PedidoAFornecedores> buscarPedidosAFornecedoresEmDefinicao() {
        return new PedidoAFornecedoresDAO().porEstado(EstadoDePedido.EM_DEFINICAO).listaDeResultados();
    }

}
