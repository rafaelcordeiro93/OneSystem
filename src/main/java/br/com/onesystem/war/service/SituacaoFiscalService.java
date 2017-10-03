package br.com.onesystem.war.service;

import br.com.onesystem.dao.SituacaoFiscalDAO;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.SituacaoFiscal;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class SituacaoFiscalService implements Serializable {

    @Inject
    private SituacaoFiscalDAO dao;

    public List<SituacaoFiscal> buscarSituacoesFiscais() {
        return dao.listaDeResultados();
    }

    public List<SituacaoFiscal> buscarSituacoesFiscaisPorOperacaoEGrupoFiscal(Operacao op, GrupoFiscal gf) {
        return dao.porOperacao(op).porGrupoFiscal(gf).ordenadoPorSequencia().listaDeResultados();
    }
}
