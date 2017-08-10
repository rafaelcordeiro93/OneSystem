package br.com.onesystem.dao;

import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;

public class TransferenciaDAO extends GenericDAO<Transferencia> {

    public TransferenciaDAO() {
        super(Transferencia.class);
        limpar();
    }

    public TransferenciaDAO porTipoLancamento() {
        TipoLancamentoBancario t = TipoLancamentoBancario.LANCAMENTO;
        where += " and transferencia.tipoLancamentoBancario = :pT ";
        parametros.put("pT", t);
        return this;
    }

    public TransferenciaDAO porId(Long id) {
        where += " and transferencia.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
