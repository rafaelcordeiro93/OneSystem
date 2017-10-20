package br.com.onesystem.war.service;

import br.com.onesystem.dao.PagamentoDAO;
import br.com.onesystem.domain.Pagamento;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class PagamentoService implements Serializable {

    @Inject
    private PagamentoDAO dao;

    public List<Pagamento> buscarPagamentos() {
        return dao.listaDeResultados();
    }

}
