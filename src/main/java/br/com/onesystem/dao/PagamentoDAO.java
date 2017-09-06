package br.com.onesystem.dao;

import br.com.onesystem.domain.Pagamento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;

public class PagamentoDAO extends GenericDAO<Pagamento> {

    public PagamentoDAO() {
        super(Pagamento.class);
    }

    public PagamentoDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and pagamento.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public PagamentoDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            where += "and pagamento.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public PagamentoDAO groupByPessoa() {
        where += "group by pagamento.pessoa ";
        return this;
    }

    public PagamentoDAO orderByMoeda() {
        where += "order by pagamento.moeda asc ";
        return this;
    }

}
