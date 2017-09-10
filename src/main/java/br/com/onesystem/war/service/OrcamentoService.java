package br.com.onesystem.war.service;

import br.com.onesystem.dao.OrcamentoDAO;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class OrcamentoService implements Serializable {

    @Inject
    private OrcamentoDAO dao;
    
    public List<Orcamento> buscarOrcamentos() {
        return dao.listaDeResultados();
    }

    public List<Orcamento> buscarOrcamentosNo(EstadoDeOrcamento estadoDeOrcamento) {
        return dao.porEstado(estadoDeOrcamento).listaDeResultados();
    }
    
}
