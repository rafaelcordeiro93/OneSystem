package br.com.onesystem.dao;

import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.Pessoa;
import java.util.Date;

public class RecebimentoDAO extends GenericDAO<Recebimento>{

    public RecebimentoDAO() {
        super(Recebimento.class);
    }

    public RecebimentoDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and recebimento.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public RecebimentoDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            where += "and recebimento.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public RecebimentoDAO groupByPessoa() {
        where += "group by recebimento.pessoa ";
        return this;
    }

    public RecebimentoDAO orderByMoeda() {
        where += "order by recebimento.moeda asc ";
        return this;
    }

}
