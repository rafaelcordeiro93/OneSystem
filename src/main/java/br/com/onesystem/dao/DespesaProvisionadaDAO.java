package br.com.onesystem.dao;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.BundleUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DespesaProvisionadaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public DespesaProvisionadaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public DespesaProvisionadaDAO buscarDespesasProvisionadas() {
        consulta += "select dp from DespesaProvisionada dp ";
        return this;
    }

    public DespesaProvisionadaDAO wAPagar() {
        consulta += "where 0 = (select count(*) from Baixa b where b.despesaProvisionada = dp.id and b.cancelada = :pBNaoCancelada) "
                + "and 0 = (select count(*) from TransferenciaDespesaProvisionada t where t.origem = dp.id) ";
        parametros.put("pBNaoCancelada", false);
        return this;
    }

    public DespesaProvisionadaDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and dp.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public DespesaProvisionadaDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial != null && dataFinal != null) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and (dp.vencimento between :pDataInicial and :pDataFinal or dp.vencimento is null) ";
        }
        return this;
    }

    public DespesaProvisionadaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            consulta += "and dp.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public DespesaProvisionadaDAO eComDivisaoDeLucro() {
        consulta += "and dp.pessoa.configuracaoCambio is not null ";
        return this;
    }

    public DespesaProvisionadaDAO groupByPessoa() {
        consulta += "group by dp.pessoa, dp.id ";
        return this;
    }
    
    public DespesaProvisionadaDAO orderByMoeda(){
        consulta += "order by dp.moeda asc ";
        return this;
    }

    public List<DespesaProvisionada> gerarDados() {
        List<DespesaProvisionada> resultado = new ArmazemDeRegistros<DespesaProvisionada>(DespesaProvisionada.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
