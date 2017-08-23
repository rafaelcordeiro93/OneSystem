package br.com.onesystem.dao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import java.util.Date;

public class ReceitaProvisionadaDAO extends GenericDAO<ReceitaProvisionada> {

    public ReceitaProvisionadaDAO() {
        super(ReceitaProvisionada.class);
        limpar();
    }

    public ReceitaProvisionadaDAO aReceber() {
        where += "and 0 = (select count(*) from Baixa b where b.cobrancaFixa = receitaProvisionada.id and b.estado = :pBNaoCancelada) ";
        parametros.put("pBNaoCancelada", EstadoDeBaixa.CANCELADO);
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
