package br.com.onesystem.war.service;

import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BaixaService implements Serializable {

    public List<Baixa> buscarBaixas() {
        return new BaixaDAO().orderByEmissaoEId().listaDeResultados();
    }

    public List<Baixa> buscarBaixasPelaData(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa, EstadoDeBaixa estado) {
        if (caixa != null) {
            return new BaixaDAO().ePorEmissaoEntre(dataInicial, dataFinal).ePorConta(conta)
                    .ePorCaixa(caixa).ePorEstadoDeBaixa(estado).eNaoCancelada().orderByEmissaoEId().listaDeResultados();
        } else {
            return new BaixaDAO().ePorEmissaoEntre(dataInicial, dataFinal).ePorConta(conta)
                    .ePorEstadoDeBaixa(estado).eNaoCancelada().orderByEmissaoEId().listaDeResultados();
        }
    }

    public BigDecimal buscarSaldoAnterior(Date dataAnterior, Conta conta, Caixa caixa, EstadoDeBaixa estado) {
        if (caixa != null) {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta, caixa, estado);
        } else {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta, null, estado);
        }
    }

    public BigDecimal buscarSaldoFinal(Date dataAnterior, Conta conta, Caixa caixa, EstadoDeBaixa estado) {
        if (caixa != null) {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta, caixa, estado);
        } else {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta, null, estado);
        }
    }

    public String buscarSaldoAnteriorFormatado(Date dataAnterior, Conta conta, Caixa caixa, EstadoDeBaixa estado) {
        return MoedaFormatter.format(conta.getMoeda(), buscarSaldoAnterior(dataAnterior, conta, caixa, estado));
    }

    public String buscarSaldoFinalFormatado(Date dataFinal, Conta conta, Caixa caixa, EstadoDeBaixa estado) {
        return MoedaFormatter.format(conta.getMoeda(), buscarSaldoFinal(dataFinal, conta, caixa, estado));
    }

    public String buscarEntradasPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        return MoedaFormatter.format(conta.getMoeda(), buscarEntradasPorDataEConta(dataInicial, dataFinal, conta, caixa));
    }

    public String buscarSaidasPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        return MoedaFormatter.format(conta.getMoeda(), buscarSaidasPorDataEConta(dataInicial, dataFinal, conta, caixa));
    }

    public BigDecimal buscarEntradasPorDataEConta(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().selectSomaBaixaValor().eNaoCancelada().ePorCaixa(caixa).ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).resultadoSomaTotal();
        } else {
            return new BaixaDAO().selectSomaBaixaValor().eNaoCancelada().ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).resultadoSomaTotal();
        }
    }

    public BigDecimal buscarSaidasPorDataEConta(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().selectSomaBaixaValor().eNaoCancelada().ePorCaixa(caixa).ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).resultadoSomaTotal().multiply(new BigDecimal(-1));
        } else {
            return new BaixaDAO().selectSomaBaixaValor().eNaoCancelada().ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).resultadoSomaTotal().multiply(new BigDecimal(-1));
        }
    }

    public BigDecimal buscarSaldoPorDataEConta(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().buscarSaldoPorDataEConta(dataInicial, dataFinal, conta, caixa);
        } else {
            return new BaixaDAO().buscarSaldoPorDataEConta(dataInicial, dataFinal, conta);
        }
    }

    public String buscarSaldoPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        return MoedaFormatter.format(conta.getMoeda(), buscarSaldoPorDataEConta(dataInicial, dataFinal, conta, caixa));
    }
}
