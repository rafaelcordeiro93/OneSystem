package br.com.onesystem.dao;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.SituacaoFiscal;

public class SituacaoFiscalDAO extends GenericDAO<SituacaoFiscal> {

    public SituacaoFiscalDAO() {
        super(SituacaoFiscal.class);
        limpar();
    }

    public SituacaoFiscalDAO porOperacao(Operacao operacao) {
        where += " and situacaoFiscal.operacao = :pOperacao";
        parametros.put("pOperacao", operacao);
        return this;
    }

    public SituacaoFiscalDAO porGrupoFiscal(GrupoFiscal grupoFiscal) {
        where += " and situacaoFiscal.grupoFiscal = :pGrupoFiscal";
        parametros.put("pGrupoFiscal", grupoFiscal);
        return this;
    }

    public SituacaoFiscalDAO ordenadoPorSequencia() {
        order += " order by situacaoFiscal.sequencia asc";
        return this;
    }

}
