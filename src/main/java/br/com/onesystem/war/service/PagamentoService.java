package br.com.onesystem.war.service;

import br.com.onesystem.dao.PagamentoDAO;
import br.com.onesystem.domain.Pagamento;
import java.io.Serializable;
import java.util.List;

public class PagamentoService implements Serializable {
    
    public List<Pagamento> buscarPagamentos() {
        return new PagamentoDAO().buscarPagamentos().listaDeResultados();
    }
    
}
