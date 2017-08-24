package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.EstadoConta;
import br.com.onesystem.valueobjects.TipoBusca;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.util.Date;

public class RelatorioDeContaAReceberBV implements Serializable {

    private Date dataInicial;
    private Date dataFinal;
    private Pessoa pessoa;
    private Conta conta;
    private TipoOperacao tipo = TipoOperacao.OUTRAS;
    private EstadoConta estadoConta = EstadoConta.RECEBIDO;
    private TipoBusca tipoBusca = TipoBusca.RECEBIMENTO;

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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoOperacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoOperacao tipo) {
        this.tipo = tipo;
    }

    public EstadoConta getEstadoConta() {
        return estadoConta;
    }

    public void setEstadoConta(EstadoConta estadoConta) {
        this.estadoConta = estadoConta;
    }

    public TipoBusca getTipoBusca() {
        return tipoBusca;
    }

    public void setTipoBusca(TipoBusca tipoBusca) {
        this.tipoBusca = tipoBusca;
    }

}
