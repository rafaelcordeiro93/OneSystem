package br.com.onesystem.dao;

import br.com.onesystem.domain.TabelaDeTributacao;

public class TabelaDeTributacaoDAO extends GenericDAO<TabelaDeTributacao> {

    public TabelaDeTributacaoDAO() {
        super(TabelaDeTributacao.class);
    }

    public TabelaDeTributacaoDAO porId(Long id) {
        where += " and d.id = :dId ";
        parametros.put("dId", id);
        return this;
    }

    public TabelaDeTributacaoDAO porNome(TabelaDeTributacao deposito) {
        where += " and d.nome = :dNome ";
        parametros.put("dNome", deposito.getNome());
        return this;
    }

}
