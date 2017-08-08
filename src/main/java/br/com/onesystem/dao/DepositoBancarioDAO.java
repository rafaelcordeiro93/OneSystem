package br.com.onesystem.dao;

import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;

public class DepositoBancarioDAO extends GenericDAO<DepositoBancario> {

    public DepositoBancarioDAO() {
        super(DepositoBancario.class);
        limpar();
    }

    public DepositoBancarioDAO porTipoLancamento() {
        TipoLancamentoBancario t = TipoLancamentoBancario.LANCAMENTO;
        where += " and depositoBancario.tipoLancamentoBancario = :pT ";
        parametros.put("pT", t);
        return this;
    }

    public DepositoBancarioDAO porId(Long id) {
        where += " and depositoBancario.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
