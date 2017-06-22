package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Conta;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class ExtratoDeContaBV implements Serializable {

    private Date dataInicial;
    private Date dataFinal;
    private Conta conta;
    private Caixa caixa;

    public ExtratoDeContaBV() {
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.set(Calendar.HOUR_OF_DAY, 23);
        dataAtual.set(Calendar.MINUTE, 59);
        dataAtual.set(Calendar.SECOND, 59);
        dataFinal = dataAtual.getTime();
        dataAtual.set(Calendar.HOUR_OF_DAY, 0); //zerando as horas, minuots e segundos..  
        dataAtual.set(Calendar.MINUTE, 0);
        dataAtual.set(Calendar.SECOND, 0);
        dataInicial = dataAtual.getTime();
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }
    
}
