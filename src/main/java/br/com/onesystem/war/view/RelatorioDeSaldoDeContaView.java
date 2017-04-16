/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeConta;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.ImpressoraDeRelatorio;
import br.com.onesystem.war.builder.RelatorioDeSaldoDeContaBV;
import br.com.onesystem.war.service.BaixaService;
import br.com.onesystem.war.service.ContaService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RelatorioDeSaldoDeContaView implements Serializable {

    private List<Conta> contas;
    private ImpressoraDeRelatorio impressora;
    private RelatorioDeSaldoDeContaBV relatorio;

    @ManagedProperty("#{baixaService}")
    private BaixaService service;

    @ManagedProperty("#{contaService}")
    private ContaService serviceConta;

    @PostConstruct
    public void construct() {
        contas = serviceConta.buscarContas();
        impressora = new ImpressoraDeRelatorio();
        relatorio = new RelatorioDeSaldoDeContaBV();
    }

    public void imprimir() {
        try {
            validarDadosDeEntrada();
            List<SaldoDeConta> lista = new ArrayList<SaldoDeConta>();
            for (Conta conta : contas) {
                BigDecimal res = service.buscarSaldoPorDataEConta(relatorio.getDataInicial(), relatorio.getDataFinal(), conta);
                SaldoDeConta saldo = new SaldoDeConta(conta, res);
                lista.add(saldo);
            }

            impressora.imprimir(lista, "RelatorioDeSaldoDeConta");

        } catch (DadoInvalidoException die) {
            ErrorMessage.print("Erro ao gerar o relatório: " + die.getMessage());
        }

    }

    private void validarDadosDeEntrada() throws EDadoInvalidoException {
        if (relatorio.getDataInicial() == null || relatorio.getDataFinal() == null) {
            throw new EDadoInvalidoException("As datas devem ser informadas!");
        }

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(relatorio.getDataInicial() != null ? relatorio.getDataInicial() : Calendar.getInstance().getTime());
        dataAtual.set(Calendar.HOUR_OF_DAY, 0);
        dataAtual.set(Calendar.MINUTE, 0);
        dataAtual.set(Calendar.SECOND, 0);
        relatorio.setDataInicial(dataAtual.getTime());

        Calendar dataFinal = Calendar.getInstance();
        dataFinal.setTime(relatorio.getDataFinal() != null ? relatorio.getDataFinal() : Calendar.getInstance().getTime());
        dataFinal.set(Calendar.HOUR_OF_DAY, 23);
        dataFinal.set(Calendar.MINUTE, 59);
        dataFinal.set(Calendar.SECOND, 59);
        relatorio.setDataFinal(dataFinal.getTime());
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public ImpressoraDeRelatorio getImpressora() {
        return impressora;
    }

    public void setImpressora(ImpressoraDeRelatorio impressora) {
        this.impressora = impressora;
    }

    public RelatorioDeSaldoDeContaBV getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(RelatorioDeSaldoDeContaBV relatorio) {
        this.relatorio = relatorio;
    }

    public BaixaService getService() {
        return service;
    }

    public void setService(BaixaService service) {
        this.service = service;
    }

    public ContaService getServiceConta() {
        return serviceConta;
    }

    public void setServiceConta(ContaService serviceConta) {
        this.serviceConta = serviceConta;
    }

}
