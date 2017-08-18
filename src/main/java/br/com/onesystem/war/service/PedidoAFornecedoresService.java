package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.PedidoAFornecedores;
import java.io.Serializable;
import java.util.List;

public class PedidoAFornecedoresService implements Serializable {

    public List<PedidoAFornecedores> buscarPedidosAFornecedores() {
        return new ArmazemDeRegistros<>(PedidoAFornecedores.class).listaTodosOsRegistros();
    }

}
