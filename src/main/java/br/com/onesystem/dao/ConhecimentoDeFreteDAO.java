package br.com.onesystem.dao;

import br.com.onesystem.domain.ConhecimentoDeFrete;

public class ConhecimentoDeFreteDAO extends GenericDAO<ConhecimentoDeFrete> {

    public ConhecimentoDeFreteDAO() {
        super(ConhecimentoDeFrete.class);
        limpar();
    }

    public ConhecimentoDeFreteDAO porId(Long id) {
        if (id != null) {
            where += " and conhecimentoDeFrete.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

}
