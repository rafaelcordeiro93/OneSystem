package br.com.onesystem.war.service;

import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.util.NumberUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BaixaService implements Serializable {

    public List<Baixa> buscarBaixas() {
        return new BaixaDAO().buscarBaixasW().orderByEmissao().listaDeResultados();
    }

    public List<Baixa> buscarBaixasPelaData(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().buscarBaixasW().ePorEmissaoEntre(dataInicial, dataFinal).ePorConta(conta)
                    .ePorCaixa(caixa).eNaoCancelada().orderByEmissao().listaDeResultados();
        } else {
            return new BaixaDAO().buscarBaixasW().ePorEmissaoEntre(dataInicial, dataFinal).ePorConta(conta)
                    .eNaoCancelada().orderByEmissao().listaDeResultados();
        }
    }

    public BigDecimal buscarSaldoAnterior(Date dataAnterior, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta, caixa);
        } else {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta);
        }
    }

    public BigDecimal buscarSaldoFinal(Date dataAnterior, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta, caixa);
        } else {
            return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta);
        }
    }

    public String buscarSaldoAnteriorFormatado(Date dataAnterior, Conta conta, Caixa caixa) {
        return MoedaFormatter.format(conta.getMoeda(), buscarSaldoAnterior(dataAnterior, conta, caixa));
    }

    public String buscarSaldoFinalFormatado(Date dataFinal, Conta conta, Caixa caixa) {
        return MoedaFormatter.format(conta.getMoeda(), buscarSaldoFinal(dataFinal, conta, caixa));
    }

    public String buscarEntradasPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        return MoedaFormatter.format(conta.getMoeda(), buscarEntradasPorDataEConta(dataInicial, dataFinal, conta, caixa));
    }

    public String buscarSaidasPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        return MoedaFormatter.format(conta.getMoeda(), buscarSaidasPorDataEConta(dataInicial, dataFinal, conta, caixa));
    }

    public BigDecimal buscarEntradasPorDataEConta(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().buscarTotalDeBaixasW().eNaoCancelada().ePorCaixa(caixa).ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).resultadoSomaTotal();
        } else {
            return new BaixaDAO().buscarTotalDeBaixasW().eNaoCancelada().ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).resultadoSomaTotal();
        }
    }

    public BigDecimal buscarSaidasPorDataEConta(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        if (caixa != null) {
            return new BaixaDAO().buscarTotalDeBaixasW().eNaoCancelada().ePorCaixa(caixa).ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).resultadoSomaTotal().multiply(new BigDecimal(-1));
        } else {
            return new BaixaDAO().buscarTotalDeBaixasW().eNaoCancelada().ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).resultadoSomaTotal().multiply(new BigDecimal(-1));
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
