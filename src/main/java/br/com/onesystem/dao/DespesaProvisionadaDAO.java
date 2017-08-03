package br.com.onesystem.dao;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import java.util.Date;

public class DespesaProvisionadaDAO extends GenericDAO<DespesaProvisionada> {

    public DespesaProvisionadaDAO() {
        super(DespesaProvisionada.class);
        limpar();
    }

    public DespesaProvisionadaDAO wAPagar() {
        where += "and 0 = (select count(*) from Baixa baixa where baixa.cobrancaFixa = despesaProvisionada.id and baixa.estado = :pBNaoCancelada) "
                + "and 0 = (select count(*) from TransferenciaDespesaProvisionada transferenciaDespesaProvisionada where transferenciaDespesaProvisionada.origem = despesaProvisionada.id) ";
        parametros.put("pBNaoCancelada", EstadoDeBaixa.CANCELADO);
        return this;
    }

    public DespesaProvisionadaDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and despesaProvisionada.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public DespesaProvisionadaDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and (despesaProvisionada.vencimento between :pDataInicial and :pDataFinal or despesaProvisionada.vencimento is null) ";
        }
        return this;
    }

    public DespesaProvisionadaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            where += "and despesaProvisionada.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public DespesaProvisionadaDAO eComDivisaoDeLucro() {
        where += "and despesaProvisionada.pessoa.configuracaoCambio is not null ";
        return this;
    }

    public DespesaProvisionadaDAO groupByPessoa() {
        where += "group by despesaProvisionada.pessoa, despesaProvisionada.id ";
        return this;
    }

    public DespesaProvisionadaDAO orderByMoeda() {
        where += "order by despesaProvisionada.moeda asc ";
        return this;
    }

}
