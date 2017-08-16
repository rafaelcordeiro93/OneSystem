package br.com.onesystem.dao;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.util.Date;

public class ChequeDAO extends GenericDAO<Cheque> {

    public ChequeDAO() {
        super(Cheque.class);
        limpar();
    }

    public ChequeDAO porNome(Cheque cheque) {
        where += " and cheque.nome = :bNome ";
        parametros.put("bNome", cheque.getEmitente());
        return this;
    }

    public ChequeDAO porEmissao(Date data) {
        where += " and cheque.emissao = :bEmissao ";
        parametros.put("bEmissao", data);
        return this;
    }

    public ChequeDAO porEstado(EstadoDeCheque estado) {
        where += " and cheque.estado = :pEstado";
        parametros.put("pEstado", estado);
        return this;
    }

    public ChequeDAO porTipoLancamento(TipoLancamento tipoLancamento) {
        where += " and cheque.tipoLancamento = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public ChequeDAO porId(Long id) {
        where += " and cheque.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
