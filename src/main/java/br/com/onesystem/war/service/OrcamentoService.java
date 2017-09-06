package br.com.onesystem.war.service;

import br.com.onesystem.dao.OrcamentoDAO;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import java.io.Serializable;
import java.util.List;

public class OrcamentoService implements Serializable {

    public List<Orcamento> buscarOrcamentos() {
        return new OrcamentoDAO().listaDeResultados();
    }

    public List<Orcamento> buscarOrcamentosNo(EstadoDeOrcamento estadoDeOrcamento) {
        return new OrcamentoDAO().porEstado(estadoDeOrcamento).listaDeResultados();
    }
    
}
