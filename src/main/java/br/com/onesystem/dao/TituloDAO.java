package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.UnidadeFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TituloDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public TituloDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public TituloDAO buscarTitulos() {
        consulta += "select t from Titulo t ";
        return this;
    }

    public TituloDAO buscarSaldoPorMoedaDeTitulos() {
        consulta += "select new br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate(t.moeda, sum(t.saldo)) from Titulo t ";
        return this;
    }

    public TituloDAO wAPagar() {
        consulta += "where t.unidadeFinanceira = :pUnidadeFinanceira ";
        parametros.put("pUnidadeFinanceira", UnidadeFinanceira.SAIDA);
        return this;
    }

    public TituloDAO wAReceber() {
        consulta += "where t.unidadeFinanceira = :pUnidadeFinanceira ";
        parametros.put("pUnidadeFinanceira", UnidadeFinanceira.ENTRADA);
        return this;
    }

    public TituloDAO eAbertas() {
        consulta += "and t.saldo > :pSaldo ";
        parametros.put("pSaldo", BigDecimal.ZERO);
        return this;
    }

    public TituloDAO ePagasOuRecebidas() {
        consulta += "and t.saldo < t.valor ";
        return this;
    }

    public TituloDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            consulta += "and t.pessoa = :pPessoa ";
            parametros.put("pPessoa", pessoa);
        }
        return this;
    }

    public TituloDAO ePorCambio(Cambio cambio) {
        if (cambio != null) {
            consulta += "and t.cambio = :pCambio ";
            parametros.put("pCambio", cambio);
        }
        return this;
    }

    public TituloDAO ePorEmissao(Date dataInicial, Date dataFinal) {
        if (dataInicial != null || dataFinal != null) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and t.emissao between :pDataInicial and :pDataFinal ";
        }
        return this;
    }

    public TituloDAO ePorVencimento(Date dataInicial, Date dataFinal) {
        if (dataInicial == null || dataFinal == dataFinal) {
            parametros.put("pDataInicial", dataInicial);
            parametros.put("pDataFinal", dataFinal);
            consulta += "and (t.vencimento between :pDataInicial and :pDataFinal or t.vencimento is null) ";
        }
        return this;
    }

    public TituloDAO eComRecepcao() {
        consulta += "and t.recepcao is not null ";
        return this;
    }

    public TituloDAO agrupadoPorMoeda() {
        consulta += "group by t.moeda ";
        return this;
    }
    
    public TituloDAO orderByMoeda(){
        consulta += "order by t.moeda asc ";
        return this;
    }

    public List<Titulo> listaDeResultados() {
        List<Titulo> resultado = new ArmazemDeRegistros<Titulo>(Titulo.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public List<SomaSaldoDeTituloPorMoedaReportTemplate> resultadoSomaPorMoeda() {
        List<SomaSaldoDeTituloPorMoedaReportTemplate> resultado = new ArmazemDeRegistros<SomaSaldoDeTituloPorMoedaReportTemplate>(SomaSaldoDeTituloPorMoedaReportTemplate.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
