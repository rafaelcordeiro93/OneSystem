package br.com.onesystem.war.view;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.ConfiguracaoFinanceiro;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.BaixaEmissaoComparator;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.ExtratoDeContaBV;
import br.com.onesystem.war.service.BaixaService;
import br.com.onesystem.war.service.ConfiguracaoFinanceiroService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
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

}
