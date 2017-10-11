package br.com.onesystem.dao;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LoteNotaFiscal;
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

    public NumeracaoDeNotaFiscalDAO porLote(LoteNotaFiscal lote) {
            where += " and numeracaoDeNotaFiscal.loteNotaFiscal = :pLote ";
            parametros.put("pLote", lote);
        return this;
    }
    
     public NumeracaoDeNotaFiscalDAO porFilial(Filial filial) {
            where += " and numeracaoDeNotaFiscal.filial = :pFilial ";
            parametros.put("pFilial", filial);
        return this;
    }
    
    
}
 