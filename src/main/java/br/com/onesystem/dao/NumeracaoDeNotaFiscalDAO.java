package br.com.onesystem.dao;

import br.com.onesystem.domain.NumeracaoDeNotaFiscal;

public class NumeracaoDeNotaFiscalDAO extends GenericDAO<NumeracaoDeNotaFiscal> {

    public NumeracaoDeNotaFiscalDAO() {
        super(NumeracaoDeNotaFiscal.class);
        limpar();
    }

    public NumeracaoDeNotaFiscalDAO porId(Long id) {
        if (id != null) {
            where += " and numeracaoDeNotaFiscal.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

}
