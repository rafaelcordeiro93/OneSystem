package br.com.onesystem.dao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import java.io.Serializable;
import java.util.Date;

public class ReceitaProvisionadaDAO extends GenericDAO<ReceitaProvisionada> implements Serializable {

    public ReceitaProvisionadaDAO() {
        super(ReceitaProvisionada.class);
        limpar();
    }

    public ReceitaProvisionadaDAO aReceber() {
        where += "and receitaProvisionada.situacaoDeCobranca = :pSituacaoDeCobranca ";
        parametros.put("pSituacaoDeCobranca", SituacaoDeCobranca.ABERTO);
        return this;
    }

    public ReceitaProvisionadaDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and receitaProvisionada.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public ReceitaProvisionadaDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and (receitaProvisionada.vencimento between :pDataInicial and :pDataFinal or receitaProvisionada.vencimento is null) ";
        }
        return this;
    }

    public ReceitaProvisionadaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            where += "and receitaProvisionada.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public ReceitaProvisionadaDAO groupByPessoa() {
        group += "group by receitaProvisionada.pessoa ";
        return this;
    }

    public ReceitaProvisionadaDAO orderByMoeda() {
        order += "order by receitaProvisionada.moeda asc ";
        return this;
    }

}
