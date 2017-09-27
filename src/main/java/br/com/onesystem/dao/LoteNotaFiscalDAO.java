package br.com.onesystem.dao;

import br.com.onesystem.domain.LoteNotaFiscal;

public class LoteNotaFiscalDAO extends GenericDAO<LoteNotaFiscal> {

    public LoteNotaFiscalDAO() {
        super(LoteNotaFiscal.class);
        limpar();
    }

    public LoteNotaFiscalDAO porId(Long id) {
        if (id != null) {
            where += " and loteNotaFiscal.id = :bId ";
            parametros.put("bId", id);
        }
        return this;
    }

    public LoteNotaFiscalDAO porNome(String nome) {
        if (nome != null) {
            where += " and loteNotaFiscal.nome = :bNome ";
            parametros.put("bNome", nome);
        }
        return this;
    }

}
