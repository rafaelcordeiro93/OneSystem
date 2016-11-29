package br.com.onesystem.dao;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.JPAUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class BaixaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public BaixaDAO() {
        limpar();
    }

    public BaixaDAO buscarBaixasW() {
        parametros.put("pValor", BigDecimal.ZERO);
        consulta += "select b from Baixa b where b.valor >= :pValor ";
        return this;
    }

    public BaixaDAO buscarTotalDeBaixasW() {
        parametros.put("pValor", BigDecimal.ZERO);
        consulta += "select sum(b.total) from Baixa b where b.valor >= :pValor ";
        return this;
    }

    public BaixaDAO eDeCambio(Cambio cambio) {
        if (cambio != null) {
            parametros.put("pCambio", cambio);
            consulta += "and b.cambio = :pCambio ";
        }
        return this;
    }

    public BaixaDAO eComDespesa() {
        consulta += "and b.despesa is not null ";
        return this;
    }

    public BaixaDAO eComDespesaProvisionada() {
        consulta += "and b.despesaProvisionada is not null ";
        return this;
    }
    
    public BaixaDAO eComReceitaProvisionada() {
        consulta += "and b.receitaProvisionada is not null ";
        return this;
    }

    public BaixaDAO eComTitulo() {
        consulta += "and b.titulo is not null ";
        return this;
    }

    public BaixaDAO eComTituloPagoRecebido() {
        consulta += "and b.titulo.saldo < b.titulo.valor ";
        return this;
    }
    
    public BaixaDAO eNaoCancelada() {
        consulta += "and b.cancelada = :pNaoCancelada ";
        parametros.put("pNaoCancelada", false);
        return this;
    }
    
    public BaixaDAO eCancelada() {
        consulta += "and b.cancelada = :pCancelada ";
        parametros.put("pCancelada", true);
        return this;
    }

    public BaixaDAO eSemTitulo() {
        consulta += "and b.titulo is null ";
        return this;
    }

    public BaixaDAO eSemDespesaProvisionada() {
        consulta += "and b.despesaProvisionada is null ";
        return this;
    }
    
     public BaixaDAO eSemReceitaProvisionada() {
        consulta += "and b.receitaProvisionada is null ";
        return this;
    }

    public BaixaDAO eDeRecepcao(Recepcao recepcao) {
        if (recepcao != null) {
            parametros.put("pRecepcao", recepcao);
            consulta = "and b.recepcao = :pRecepcao ";
        }
        return this;
    }

    public BaixaDAO ePorEmissaoEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        consulta += "and b.emissao between :pDataInicial and :pDataFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDoTituloEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataETInicial", dataInicial);
        parametros.put("pDataETFinal", dataFinal);
        consulta += "and b.titulo.emissao between :pDataETInicial and :pDataETFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDaDespesaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataEDPInicial", dataInicial);
        parametros.put("pDataEDPFinal", dataFinal);
        consulta += "and b.despesaProvisionada.emissao between :pDataEDPInicial and :pDataEDPFinal ";
        return this;
    }
    
    public BaixaDAO ePorEmissaoDaReceitaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataEDPInicial", dataInicial);
        parametros.put("pDataEDPFinal", dataFinal);
        consulta += "and b.receitaProvisionada.emissao between :pDataEDPInicial and :pDataEDPFinal ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeTituloEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVTInicial", dataInicial);
        parametros.put("pDataVTFinal", dataFinal);
        consulta += "and (b.titulo.vencimento between :pDataVTInicial and :pDataVTFinal or b.titulo.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeDespesaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVDPInicial", dataInicial);
        parametros.put("pDataVDPFinal", dataFinal);
        consulta += "and (b.despesaProvisionada.vencimento between :pDataVDPInicial and :pDataVDPFinal or b.despesaProvisionada.vencimento is null) ";
        return this;
    }
    
    public BaixaDAO ePorVencimentoDeReceitaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVDPInicial", dataInicial);
        parametros.put("pDataVDPFinal", dataFinal);
        consulta += "and (b.receitaProvisionada.vencimento between :pDataVDPInicial and :pDataVDPFinal or b.receitaProvisionada.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorEmissaoMenorIgualDa(Date data) {
        parametros.put("pData", data);
        consulta += "and b.emissao <= :pData ";
        return this;
    }

    public BaixaDAO ePorEmissaoMenorDa(Date data) {
        parametros.put("pMData", data);
        consulta += "and b.emissao < :pMData ";
        return this;
    }

    public BaixaDAO eEntrada() {
        parametros.put("pUnidadeEntrada", OperacaoFinanceira.ENTRADA);
        consulta += "and b.unidadeFinanceira = :pUnidadeEntrada ";
        return this;
    }

    public BaixaDAO eSaida() {
        parametros.put("pUnidadeSaida", OperacaoFinanceira.SAIDA);
        consulta += "and b.unidadeFinanceira = :pUnidadeSaida ";
        return this;
    }

    public BaixaDAO ePorConta(Conta conta) {
        if (conta != null) {
            parametros.put("pConta", conta);
            consulta += "and b.conta = :pConta ";
        }
        return this;
    }

    public BaixaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            parametros.put("pPessoa", pessoa);
            consulta += "and b.pessoa = :pPessoa ";
        }
        return this;
    }

    public BaixaDAO orderByEmissao() {
        consulta += "order by b.emissao asc ";
        return this;
    }
    
    public BaixaDAO orderByMoeda(){
        consulta += "order by b.conta.moeda asc";
        return this;
    }

    public BigDecimal buscarSaldoAnterior(Date data, Conta conta) {
        BigDecimal entradas = buscarTotalDeBaixasW().ePorEmissaoMenorDa(data).eEntrada().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal saidas = buscarTotalDeBaixasW().ePorEmissaoMenorDa(data).eSaida().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado;
    }

    public BigDecimal buscarSaldoFinal(Date dataFinal, Conta conta) {

        BigDecimal entradas = buscarTotalDeBaixasW().ePorEmissaoMenorIgualDa(dataFinal).eEntrada().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal saidas = buscarTotalDeBaixasW().ePorEmissaoMenorIgualDa(dataFinal).eSaida().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado == null ? BigDecimal.ZERO : resultado;
    }

    public BigDecimal buscarSaldoPorDataEConta(Date dataInicial, Date dataFinal, Conta conta) {
        BigDecimal entradas = buscarTotalDeBaixasW().ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal saidas = buscarTotalDeBaixasW().ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado == null ? BigDecimal.ZERO : resultado;
    }

    public Date buscarUltimoPagamentoDe(Titulo titulo) {
        EntityManager manager = JPAUtil.getEntityManager();
        String consulta = "select max(b.emissao) from Baixa b where b.titulo = :pTitulo and b.cancelada = :pNaoCancelada";
        TypedQuery<Date> query = manager.createQuery(consulta, Date.class);
        query.setParameter("pTitulo", titulo);
        query.setParameter("pNaoCancelada", false);
        return query.getSingleResult();
    }

    public Baixa resultadoUnico() {
        Baixa resultado = new ArmazemDeRegistros<Baixa>(Baixa.class)
                .resultadoUnicoDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public List<Baixa> listaDeResultados() {
        List<Baixa> resultado = new ArmazemDeRegistros<Baixa>(Baixa.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public BigDecimal resultadoSomaTotal() {
        BigDecimal resultado = new ArmazemDeRegistros<BigDecimal>(BigDecimal.class)
                .resultadoUnicoDaConsulta(consulta, parametros);
        limpar();
        return resultado == null ? BigDecimal.ZERO : resultado;
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

}
