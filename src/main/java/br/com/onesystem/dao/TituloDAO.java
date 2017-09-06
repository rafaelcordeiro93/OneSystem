package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TituloDAO extends GenericDAO<Titulo> {

    public TituloDAO() {
        super(Titulo.class);
        limpar();
    }

    protected void limpar() {
        query = "select t from Titulo t ";
        join = " ";
        where = " where t.id != 0 ";
        order = " ";
        group = " ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public TituloDAO buscarSaldoPorMoedaDeTitulos() {
        query = "select new br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate(t.moeda, sum(t.saldo)) from Titulo t ";
        return this;
    }

    public TituloDAO aPagar() {
        where += "and t.operacaoFinanceira = :pOperacaoFinanceira ";
        parametros.put("pOperacaoFinanceira", OperacaoFinanceira.SAIDA);
        return this;
    }

    public TituloDAO aReceber() {
        where += "and t.operacaoFinanceira = :pOperacaoFinanceira ";
        parametros.put("pOperacaoFinanceira", OperacaoFinanceira.ENTRADA);
        return this;
    }

    public TituloDAO eAbertas() {
        where += "and t.saldo > :pSaldo ";
        parametros.put("pSaldo", BigDecimal.ZERO);
        return this;
    }

    public TituloDAO ePagasOuRecebidas() {
        where += "and t.saldo < t.valor ";
        return this;
    }

    public TituloDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            where += "and t.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public TituloDAO ePorFaturaEmitida(FaturaEmitida fatura) {
        if (fatura != null) {
            where += "and t.faturaEmitida = :pFaturaEmitida ";
            parametros.put("pFaturaEmitida", fatura);
        }
        return this;
    }

    public TituloDAO ePorCambio(Cambio cambio) {
        if (cambio != null) {
            where += "and t.cambio = :pCambio ";
            parametros.put("pCambio", cambio);
        }
        return this;
    }

    public TituloDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial != null || dataFinal != null) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and t.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public TituloDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            where += "and ((t.vencimento between :pDataInicial and :pDataFinal) or t.vencimento is null) ";
        }
        return this;
    }

    public TituloDAO eComRecepcao() {
        where += "and t.recepcao is not null ";
        return this;
    }

    public TituloDAO agrupadoPorMoeda() {
        group += "group by t.moeda ";
        return this;
    }

    public TituloDAO orderByMoeda() {
        order += "order by t.moeda asc ";
        return this;
    }

//    public List<SomaSaldoDeTituloPorMoedaReportTemplate> resultadoSomaPorMoeda() {
//        List<SomaSaldoDeTituloPorMoedaReportTemplate> resultado = new ArmazemDeRegistros<SomaSaldoDeTituloPorMoedaReportTemplate>(SomaSaldoDeTituloPorMoedaReportTemplate.class)
//                .listaRegistrosDaConsulta(getConsulta(), parametros);
//        limpar();
//        return resultado;
//    }
}
