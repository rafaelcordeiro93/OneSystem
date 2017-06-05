package br.com.onesystem.dao;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.BundleUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceitaProvisionadaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ReceitaProvisionadaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ReceitaProvisionadaDAO buscarReceitasProvisionadas() {
        consulta += "select rp from ReceitaProvisionada rp ";
        return this;
    }

    public ReceitaProvisionadaDAO wAReceber() {
//        consulta += "where 0 = (select count(*) from Baixa b where b.receitaProvisionada = rp.id and b.cancelada = :pBNaoCancelada) ";
//        parametros.put("pBNaoCancelada", false);
        return this;
    }

    public ReceitaProvisionadaDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and rp.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public ReceitaProvisionadaDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and (rp.vencimento between :pDataInicial and :pDataFinal or rp.vencimento is null) ";
        }
        return this;
    }

    public ReceitaProvisionadaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            consulta += "and rp.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public ReceitaProvisionadaDAO groupByPessoa() {
        consulta += "group by rp.pessoa ";
        return this;
    }

    public ReceitaProvisionadaDAO orderByMoeda() {
        consulta += "order by rp.moeda asc ";
        return this;
    }

    public List<ReceitaProvisionada> listaDeResultados() {
        List<ReceitaProvisionada> resultado = new ArmazemDeRegistros<ReceitaProvisionada>(ReceitaProvisionada.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
