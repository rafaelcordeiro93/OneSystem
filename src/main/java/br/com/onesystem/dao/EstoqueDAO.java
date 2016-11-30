package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.UnidadeFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstoqueDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public EstoqueDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public EstoqueDAO buscarEstoques() {
        consulta += "select t from Estoque t ";
        return this;
    }

    public EstoqueDAO buscarSaldoPorMoedaDeEstoques() {
        consulta += "select new br.com.onesystem.reportTemplate.SomaSaldoDeEstoquePorMoedaReportTemplate(t.moeda, sum(t.saldo)) from Estoque t ";
        return this;
    }

    public EstoqueDAO wAPagar() {
        consulta += "where t.unidadeFinanceira = :pUnidadeFinanceira ";
        parametros.put("pUnidadeFinanceira", UnidadeFinanceira.SAIDA);
        return this;
    }

    public EstoqueDAO wAReceber() {
        consulta += "where t.unidadeFinanceira = :pUnidadeFinanceira ";
        parametros.put("pUnidadeFinanceira", UnidadeFinanceira.ENTRADA);
        return this;
    }

    public EstoqueDAO eAbertas() {
        consulta += "and t.saldo > :pSaldo ";
        parametros.put("pSaldo", BigDecimal.ZERO);
        return this;
    }

    public EstoqueDAO ePagasOuRecebidas() {
        consulta += "and t.saldo < t.valor ";
        return this;
    }

    public EstoqueDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            consulta += "and t.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public EstoqueDAO ePorCambio(Cambio cambio) {
        if (cambio != null) {
            consulta += "and t.cambio = :pCambio ";
            parametros.put("pCambio", cambio);
        }
        return this;
    }

    public EstoqueDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial != null || dataFinal != null) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and t.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public EstoqueDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and (t.vencimento between :pDataInicial and :pDataFinal or t.vencimento is null) ";
        }
        return this;
    }

    public EstoqueDAO eComRecepcao() {
        consulta += "and t.recepcao is not null ";
        return this;
    }

    public EstoqueDAO agrupadoPorMoeda() {
        consulta += "group by t.moeda ";
        return this;
    }
    
    public EstoqueDAO orderByMoeda(){
        consulta += "order by t.moeda asc ";
        return this;
    }

    public List<Estoque> listaDeResultados() {
        List<Estoque> resultado = new ArmazemDeRegistros<Estoque>(Estoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
