package br.com.onesystem.war.view;

import br.com.onesystem.dao.ChequeDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.services.BaixaEmissaoComparator;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.ExtratoDeContaBV;
import br.com.onesystem.war.service.BaixaService;
import br.com.onesystem.war.service.ConfiguracaoFinanceiroService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ExtratoDeContaView extends BasicMBImpl<Baixa, BaixaBV> implements Serializable {

    private Date hoje = new Date();
    private ExtratoDeContaBV extrato;
    private List<Baixa> baixas;
    private BigDecimal saldoAnterior;

    @Inject
    private BaixaService service;

    @Inject
    private ConfiguracaoFinanceiroService serviceFinanceiro;

    @PostConstruct
    public void init() {
        extrato = new ExtratoDeContaBV();
        buscarConfiguracaoDeConta();
    }

    private void buscarConfiguracaoDeConta() {
        ConfiguracaoFinanceiro conf = serviceFinanceiro.buscar();
        extrato.setConta(conf == null ? null : conf.getContaPadrao());
        if (extrato.getConta() != null) {
            baixas = service.buscarBaixasPelaData(extrato.getDataInicial(), extrato.getDataFinal(), extrato.getConta(), extrato.getCaixa());
            Collections.sort(baixas, new BaixaEmissaoComparator());
        }
    }

    public void atualizar() {
        try {
            validaConta();
            Calendar dataAtual = Calendar.getInstance();
            dataAtual.setTime(extrato.getDataInicial() != null ? extrato.getDataInicial() : Calendar.getInstance().getTime());
            dataAtual.set(Calendar.HOUR_OF_DAY, 0);
            dataAtual.set(Calendar.MINUTE, 0);
            dataAtual.set(Calendar.SECOND, 0);
            extrato.setDataInicial(dataAtual.getTime());
            dataAtual.setTime(extrato.getDataFinal() != null ? extrato.getDataFinal() : Calendar.getInstance().getTime());
            dataAtual.set(Calendar.HOUR_OF_DAY, 23);
            dataAtual.set(Calendar.MINUTE, 59);
            dataAtual.set(Calendar.SECOND, 59);
            extrato.setDataFinal(dataAtual.getTime());
            baixas = service.buscarBaixasPelaData(extrato.getDataInicial(), extrato.getDataFinal(), extrato.getConta(), extrato.getCaixa());
            saldoAnterior = service.buscarSaldoAnterior(extrato.getDataInicial(), extrato.getConta(), extrato.getCaixa());
            Collections.sort(baixas, new BaixaEmissaoComparator());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void validaConta() throws DadoInvalidoException {
        if (extrato.getConta() == null) {
            baixas = new ArrayList<>();
            throw new ADadoInvalidoException(new BundleUtil().getMessage("conta_not_null"));
        }
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Conta) {
            this.extrato.setConta((Conta) event.getObject());
            atualizar();
        } else if (obj instanceof Transferencia) {
            atualizar();
            InfoMessage.adicionado();
        } else if (obj instanceof Caixa) {
            this.extrato.setCaixa((Caixa) event.getObject());
            atualizar();
        }
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public BigDecimal buscarSaldoAnterior(Baixa baixa) {
        BigDecimal resultado = (buscarEntradas(baixa).subtract(buscarSaidas(baixa))).add(saldoAnterior);
        return resultado;
    }

    public BigDecimal buscarEntradas(Baixa baixa) {
        return baixas.stream().filter(b -> b.getEmissao().compareTo(baixa.getEmissao()) <= 0 && b.getId().compareTo(baixa.getId()) <= 0).filter(b -> b.getNaturezaFinanceira() == OperacaoFinanceira.ENTRADA)
                .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal buscarSaidas(Baixa baixa) {
        return baixas.stream().filter(b -> b.getEmissao().compareTo(baixa.getEmissao()) <= 0 && b.getId().compareTo(baixa.getId()) <= 0).filter(b -> b.getNaturezaFinanceira() == OperacaoFinanceira.SAIDA)
                .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getEntradas() {
        return baixas.stream().filter(b -> b.getNaturezaFinanceira() == OperacaoFinanceira.ENTRADA)
                .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getEntradasFormatado() {
        return MoedaFormatter.format(extrato.getConta().getMoeda(), getEntradas());
    }

    public BigDecimal getSaidas() {
        return baixas.stream().filter(b -> b.getNaturezaFinanceira() == OperacaoFinanceira.SAIDA)
                .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getSaidasFormatado() {
        return MoedaFormatter.format(extrato.getConta().getMoeda(), BigDecimal.ZERO.subtract(getSaidas()));
    }

    public BigDecimal getSaldo() {
        return getEntradas().subtract(getSaidas());
    }

    public String getSaldoFormatado() {
        return MoedaFormatter.format(extrato.getConta().getMoeda(), getSaldo());
    }

    public BigDecimal getSaldoAcumulado() {
        return getSaldo().add(saldoAnterior);
    }

    public String getSaldoAcumuladoFormatado() {
        return MoedaFormatter.format(extrato.getConta().getMoeda(), getSaldoAcumulado());
    }

    public BaixaService getService() {
        return service;
    }

    public void setService(BaixaService service) {
        this.service = service;
    }

    public Date getHoje() {
        return hoje;
    }

    public void setHoje(Date hoje) {
        this.hoje = hoje;
    }

    public ExtratoDeContaBV getExtrato() {
        return extrato;
    }

    public void setExtrato(ExtratoDeContaBV extrato) {
        this.extrato = extrato;
    }

    public ConfiguracaoFinanceiroService getServiceFinanceiro() {
        return serviceFinanceiro;
    }

    public void setServiceFinanceiro(ConfiguracaoFinanceiroService serviceFinanceiro) {
        this.serviceFinanceiro = serviceFinanceiro;
    }

    @Override
    public void limparJanela() {
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public String getSaldoAnteriorFormatado() {
        return MoedaFormatter.format(extrato.getConta().getMoeda(), saldoAnterior);
    }

}
