package br.com.onesystem.war.view;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.CambioEmpresa;
import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.services.BaixaEmissaoComparator;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
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
    private String lancamentoCompensacao;
    private List<String> listEstado = new ArrayList<>();

    @Inject
    private BaixaService service;

    @Inject
    private ConfiguracaoFinanceiroService serviceFinanceiro;

    @PostConstruct
    public void init() {
        limparJanela();
        buscarConfiguracaoDeConta();
    }

    @Override
    public void limparJanela() {
        extrato = new ExtratoDeContaBV();
        lancamentoCompensacao = new BundleUtil().getLabel("Lancamento");
        listEstado.add(new BundleUtil().getLabel("Lancamento"));
        listEstado.add(new BundleUtil().getLabel("Compensacao"));
    }

    private void buscarConfiguracaoDeConta() {
        ConfiguracaoFinanceiro conf = serviceFinanceiro.buscar();
        extrato.setConta(conf == null ? null : conf.getContaPadrao());
        if (extrato.getConta() != null && lancamentoCompensacao.equals(new BundleUtil().getLabel("Lancamento"))) {
            atualizar();
        }
    }

    public void atualizar() {
        try {
            validaConta();
            setDataAtual();
            if (extrato.getConta() != null && lancamentoCompensacao.equals(new BundleUtil().getLabel("Lancamento"))) {
                baixas = service.buscarBaixasPelaData(extrato.getDataInicial(), extrato.getDataFinal(), extrato.getConta(), extrato.getCaixa(), null);
                saldoAnterior = service.buscarSaldoAnterior(extrato.getDataInicial(), extrato.getConta(), extrato.getCaixa(), null);
                Collections.sort(baixas, new BaixaEmissaoComparator());
            } else if (extrato.getConta() != null && lancamentoCompensacao.equals(new BundleUtil().getLabel("Compensacao"))) {
                baixas = service.buscarBaixasPelaData(extrato.getDataInicial(), extrato.getDataFinal(), extrato.getConta(), extrato.getCaixa(), EstadoDeBaixa.EFETIVADO);
                saldoAnterior = service.buscarSaldoAnterior(extrato.getDataInicial(), extrato.getConta(), extrato.getCaixa(), EstadoDeBaixa.EFETIVADO);
                Collections.sort(baixas, new BaixaEmissaoComparator());
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void setDataAtual() {
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
        } else if (obj instanceof Transferencia || obj instanceof DepositoBancario
                || obj instanceof SaqueBancario || obj instanceof CambioEmpresa || obj instanceof LancamentoBancario) {
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
        if (baixa != null) {
            return baixas.stream().filter(b -> b.getEmissao().compareTo(baixa.getEmissao()) <= 0 && b.getId().compareTo(baixa.getId()) <= 0).filter(b -> b.getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA)
                    .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal buscarSaidas(Baixa baixa) {
        if (baixa != null) {
            return baixas.stream().filter(b -> b.getEmissao().compareTo(baixa.getEmissao()) <= 0 && b.getId().compareTo(baixa.getId()) <= 0).filter(b -> b.getOperacaoFinanceira() == OperacaoFinanceira.SAIDA)
                    .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getEntradas() {
        if (baixas != null) {
            return baixas.stream().filter(b -> b.getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA)
                    .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getSaidas() {
        if (baixas != null) {
            return baixas.stream().filter(b -> b.getOperacaoFinanceira() == OperacaoFinanceira.SAIDA)
                    .map(Baixa::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public String getEntradasFormatado() {
        return MoedaFormatter.format(extrato.getConta().getMoeda(), getEntradas());

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
        try {
            return getSaldo().add(saldoAnterior);
        } catch (NullPointerException npe) {
            return BigDecimal.ZERO;
        }
    }

    public String getSaldoAcumuladoFormatado() {
        return MoedaFormatter.format(extrato.getConta().getMoeda(), getSaldoAcumulado());
    }

    public String getLancamentoCompensacao() {
        return lancamentoCompensacao;
    }

    public void setLancamentoCompensacao(String lancamentoCompensacao) {
        this.lancamentoCompensacao = lancamentoCompensacao;
    }

    public List<String> getListEstado() {
        return listEstado;
    }

    public void setListEstado(List<String> listEstado) {
        this.listEstado = listEstado;
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
