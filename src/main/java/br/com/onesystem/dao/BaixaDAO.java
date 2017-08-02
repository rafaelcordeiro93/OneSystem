package br.com.onesystem.dao;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.util.JPAUtil;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class BaixaDAO extends GenericDAO<Baixa> {

    public BaixaDAO() {
        super(Baixa.class);
        limpar();
    }

    public BaixaDAO buscarTotalDeBaixasW() {
        parametros.put("pValor", BigDecimal.ZERO);
        where += "select sum(baixa.valor) from Baixa baixa where baixa.valor >= :pValor ";
        return this;
    }

    public BaixaDAO eDeCambio(Cambio cambio) {
        if (cambio != null) {
            parametros.put("pCambio", cambio);
            where += "and baixa.cambio = :pCambio ";
        }
        return this;
    }

    public BaixaDAO eComDespesa() {
        where += "and baixa.despesa is not null ";
        return this;
    }

    public BaixaDAO eComDespesaProvisionada() {
        where += "and baixa.despesaProvisionada is not null ";
        return this;
    }

    public BaixaDAO eComReceitaProvisionada() {
        where += "and baixa.receitaProvisionada is not null ";
        return this;
    }

    public BaixaDAO eComTitulo() {
        where += "and baixa.titulo is not null ";
        return this;
    }

    public BaixaDAO eComTituloPagoRecebido() {
        where += "and baixa.titulo.saldo < baixa.titulo.valor ";
        return this;
    }

    public BaixaDAO eNaoCancelada() {
        where += "and baixa.estado <> :pNaoCancelada ";
        parametros.put("pNaoCancelada", EstadoDeBaixa.CANCELADO);
        return this;
    }

    public BaixaDAO eCancelada() {
        where += "and baixa.estado = :pCancelada ";
        parametros.put("pCancelada", EstadoDeBaixa.CANCELADO);
        return this;
    }

    public BaixaDAO eSemTitulo() {
        where += "and baixa.titulo is null ";
        return this;
    }

    public BaixaDAO eSemDespesaProvisionada() {
        where += "and baixa.despesaProvisionada is null ";
        return this;
    }

    public BaixaDAO eSemReceitaProvisionada() {
        where += "and baixa.receitaProvisionada is null ";
        return this;
    }

    public BaixaDAO eDeRecepcao(Recepcao recepcao) {
        if (recepcao != null) {
            parametros.put("pRecepcao", recepcao);
            where = "and baixa.recepcao = :pRecepcao ";
        }
        return this;
    }

    public BaixaDAO ePorEmissaoEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        where += "and baixa.emissao between :pDataInicial and :pDataFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDoTituloEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataETInicial", dataInicial);
        parametros.put("pDataETFinal", dataFinal);
        where += "and baixa.titulo.emissao between :pDataETInicial and :pDataETFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDaDespesaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataEDPInicial", dataInicial);
        parametros.put("pDataEDPFinal", dataFinal);
        where += "and baixa.despesaProvisionada.emissao between :pDataEDPInicial and :pDataEDPFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDaReceitaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataEDPInicial", dataInicial);
        parametros.put("pDataEDPFinal", dataFinal);
        where += "and baixa.receitaProvisionada.emissao between :pDataEDPInicial and :pDataEDPFinal ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeTituloEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVTInicial", dataInicial);
        parametros.put("pDataVTFinal", dataFinal);
        where += "and (baixa.titulo.vencimento between :pDataVTInicial and :pDataVTFinal or baixa.titulo.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeDespesaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVDPInicial", dataInicial);
        parametros.put("pDataVDPFinal", dataFinal);
        where += "and (baixa.despesaProvisionada.vencimento between :pDataVDPInicial and :pDataVDPFinal or baixa.despesaProvisionada.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeReceitaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVDPInicial", dataInicial);
        parametros.put("pDataVDPFinal", dataFinal);
        where += "and (baixa.receitaProvisionada.vencimento between :pDataVDPInicial and :pDataVDPFinal or baixa.receitaProvisionada.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorEmissaoMenorIgualDa(Date data) {
        parametros.put("pData", data);
        where += "and baixa.emissao <= :pData ";
        return this;
    }

    public BaixaDAO ePorEmissaoMenorDa(Date data) {
        parametros.put("pMData", data);
        where += "and baixa.emissao < :pMData ";
        return this;
    }

    public BaixaDAO eEntrada() {
        parametros.put("pNaturezaFinanceira", OperacaoFinanceira.ENTRADA);
        where += "and baixa.naturezaFinanceira = :pNaturezaFinanceira ";
        return this;
    }

    public BaixaDAO eSaida() {
        parametros.put("pNaturezaSaida", OperacaoFinanceira.SAIDA);
        where += "and baixa.naturezaFinanceira = :pNaturezaSaida ";
        return this;
    }

    public BaixaDAO ePorConta(Conta conta) {
        if (conta != null) {
            parametros.put("pConta", conta);
            where += "and baixa.cotacao.conta = :pConta ";
        }
        return this;
    }

    public BaixaDAO ePorCaixa(Caixa caixa) {
        if (caixa != null) {
            parametros.put("pCaixa", caixa);
            where += "and baixa.caixa = :pCaixa ";
        }
        return this;
    }

    public BaixaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            parametros.put("pPessoa", pessoa);
            where += "and baixa.pessoa = :pPessoa ";
        }
        return this;
    }

    public BaixaDAO ePorValorPorCotacao(ValorPorCotacao valorPorCotacao) {
        if (valorPorCotacao != null) {
            parametros.put("pValorPorCotacao", valorPorCotacao);
            where += "and baixa.valorPorCotacao = :pValorPorCotacao ";
        }
        return this;
    }

    public BaixaDAO ePorTipoDeCobranca(TipoDeCobranca tipo) {
        if (tipo != null) {
            parametros.put("pTipo", tipo);
            where += "and baixa.tipoDeCobranca = :pTipo ";
        }
        return this;
    }

    public BaixaDAO ePorFormaDeCobranca(FormaDeCobranca forma) {
        if (forma != null) {
            parametros.put("pForma", forma);
            where += "and baixa.formaDeCobranca = :pForma ";
        }
        return this;
    }

    public BaixaDAO orderByEmissaoEId() {
        where += "order by baixa.emissao,baixa.id asc ";
        return this;
    }

    public BaixaDAO orderByMoeda() {
        where += "order by baixa.conta.moeda asc";
        return this;
    }

    public BigDecimal buscarSaldoAnterior(Date data, Conta conta) {
        BigDecimal entradas = buscarTotalDeBaixasW().ePorEmissaoMenorDa(data).eEntrada().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal saidas = buscarTotalDeBaixasW().ePorEmissaoMenorDa(data).eSaida().ePorConta(conta).eNaoCancelada().resultadoSomaTotal();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado;
    }

    public BigDecimal buscarSaldoAnterior(Date data, Conta conta, Caixa caixa) {
        BigDecimal entradas = buscarTotalDeBaixasW().ePorEmissaoMenorDa(data).eEntrada().ePorConta(conta).ePorCaixa(caixa).eNaoCancelada().resultadoSomaTotal();
        BigDecimal saidas = buscarTotalDeBaixasW().ePorEmissaoMenorDa(data).eSaida().ePorConta(conta).ePorCaixa(caixa).eNaoCancelada().resultadoSomaTotal();
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

    public BigDecimal buscarSaldoPorDataEConta(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        BigDecimal entradas = buscarTotalDeBaixasW().ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).ePorCaixa(caixa).eNaoCancelada().resultadoSomaTotal();
        BigDecimal saidas = buscarTotalDeBaixasW().ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).ePorCaixa(caixa).eNaoCancelada().resultadoSomaTotal();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado == null ? BigDecimal.ZERO : resultado;
    }

    public Date buscarUltimoPagamentoDe(Titulo titulo) {
        EntityManager manager = JPAUtil.getEntityManager();
        String where = "select max(baixa.emissao) from Baixa baixa where baixa.titulo = :pTitulo and baixa.cancelada = :pNaoCancelada";
        TypedQuery<Date> query = manager.createQuery(where, Date.class);
        query.setParameter("pTitulo", titulo);
        query.setParameter("pNaoCancelada", false);
        return query.getSingleResult();
    }

    public BigDecimal resultadoSomaTotal() {
        BigDecimal resultado = new ArmazemDeRegistros<BigDecimal>(BigDecimal.class)
                .resultadoUnicoDaConsulta(where, parametros);
        limpar();
        return resultado == null ? BigDecimal.ZERO : resultado;
    }

}
