package br.com.onesystem.war.service;

import br.com.onesystem.dao.PedidoAFornecedoresDAO;
import br.com.onesystem.domain.PedidoAFornecedores;
import br.com.onesystem.valueobjects.EstadoDePedido;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class PedidoAFornecedoresService implements Serializable {

    @Inject
    private PedidoAFornecedoresDAO dao;
    
    public List<PedidoAFornecedores> buscarPedidosAFornecedores() {
        return dao.listaDeResultados();
    }

    public List<PedidoAFornecedores> buscarPedidosAFornecedoresEmDefinicao() {
        return dao.porEstado(EstadoDePedido.EM_DEFINICAO).listaDeResultados();
    }

}
