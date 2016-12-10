package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EstoqueBV implements Serializable {

    private Long id;
    private Deposito deposito;
    private Item item;
    private BigDecimal saldo;
    private OperacaoFisica tipo;
    private Date emissao = new Date();

    public EstoqueBV(Estoque estoqueSelecionado) {
        this.id = estoqueSelecionado.getId();
        this.deposito = estoqueSelecionado.getDeposito();
        this.item = estoqueSelecionado.getItem();
        this.saldo = estoqueSelecionado.getSaldo();
        this.tipo = estoqueSelecionado.getTipo();
        this.emissao = estoqueSelecionado.getEmissao();

    }

    public EstoqueBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public OperacaoFisica getTipo() {
        return tipo;
    }

    public void setTipo(OperacaoFisica tipo) {
        this.tipo = tipo;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

 
    public Estoque construir() throws DadoInvalidoException {
        return new EstoqueBuilder().comSaldo(saldo)
                .comItem(item).comDeposito(deposito).comTipo(tipo).construir();
    }

    public Estoque construirComID() throws DadoInvalidoException {
        return new EstoqueBuilder().comID(id).comSaldo(saldo)
                .comItem(item).comDeposito(deposito).comTipo(tipo).construir();
    }
}
