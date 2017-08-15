package br.com.onesystem.dao;

import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.valueobjects.EstadoDeOrcamento;

public class OrcamentoDAO extends GenericDAO<Orcamento>{

    public OrcamentoDAO() {
        super(Orcamento.class);
    }

    public OrcamentoDAO porId(Long id) {
        where += " and o.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public OrcamentoDAO porEstado(EstadoDeOrcamento estado) {
        where += " and o.estado = :oEstado ";
        parametros.put("oEstado", estado);
        return this;
    }

}
