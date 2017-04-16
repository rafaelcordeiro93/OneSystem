package br.com.onesystem.war.service;

import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.NumberUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BaixaService implements Serializable {

    public List<Baixa> buscarBaixas() {
        return new BaixaDAO().buscarBaixasW().orderByEmissao().listaDeResultados();
    }

    public List<Baixa> buscarBaixasPelaData(Date dataInicial, Date dataFinal, Conta conta) {
        return new BaixaDAO().buscarBaixasW().ePorEmissaoEntre(dataInicial, dataFinal).ePorConta(conta).eNaoCancelada().orderByEmissao().listaDeResultados();
    }

    public BigDecimal buscarSaldoAnterior(Date dataAnterior, Conta conta) {
        return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta);
    }

    public BigDecimal buscarSaldoFinal(Date dataAnterior, Conta conta) {
        return new BaixaDAO().buscarSaldoAnterior(dataAnterior, conta);
    }

    public String buscarSaldoAnteriorFormatado(Date dataAnterior, Conta conta) {
        return conta.getMoeda().getSigla() + " " + NumberUtils.format(buscarSaldoAnterior(dataAnterior, conta));
    }

    public String buscarSaldoFinalFormatado(Date dataFinal, Conta conta) {
        return conta.getMoeda().getSigla() + " " + NumberUtils.format(buscarSaldoFinal(dataFinal, conta));
    }

    public String buscarEntradasPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta) {
        return conta.getMoeda().getSigla() + " " + NumberUtils.format(buscarEntradasPorDataEConta(dataInicial, dataFinal, conta));
    }

    public String buscarSaidasPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta) {
        return conta.getMoeda().getSigla() + " " + NumberUtils.format(buscarSaidasPorDataEConta(dataInicial, dataFinal, conta));
    }

    public BigDecimal buscarEntradasPorDataEConta(Date dataInicial, Date dataFinal, Conta conta) {
        return new BaixaDAO().buscarTotalDeBaixasW().eNaoCancelada().ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).resultadoSomaTotal();
    }

    public BigDecimal buscarSaidasPorDataEConta(Date dataInicial, Date dataFinal, Conta conta) {
        return new BaixaDAO().buscarTotalDeBaixasW().eNaoCancelada().ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).resultadoSomaTotal().multiply(new BigDecimal(-1));
    }

    public BigDecimal buscarSaldoPorDataEConta(Date dataInicial, Date dataFinal, Conta conta) {
        return new BaixaDAO().buscarSaldoPorDataEConta(dataInicial, dataFinal, conta);
    }

    public String buscarSaldoPorDataEContaFormatado(Date dataInicial, Date dataFinal, Conta conta) {
        return conta.getMoeda().getSigla() + " " + NumberUtils.format(buscarSaldoPorDataEConta(dataInicial, dataFinal, conta));
    }   
}
