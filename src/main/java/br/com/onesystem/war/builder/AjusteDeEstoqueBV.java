package br.com.onesystem.war.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.builder.AjusteDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AjusteDeEstoqueBV implements Serializable {

    private Long id;
    private String observacao;
    private Deposito deposito;
    private Item item;
    private BigDecimal quantidade;
    private Date data = new Date();

    public AjusteDeEstoqueBV(AjusteDeEstoque ajusteDeEstoqueSelecionada) {
        this.id = ajusteDeEstoqueSelecionada.getId();
        this.observacao = ajusteDeEstoqueSelecionada.getObservacao();
        this.deposito = ajusteDeEstoqueSelecionada.getDeposito();
        this.item = ajusteDeEstoqueSelecionada.getItem();
        this.quantidade = ajusteDeEstoqueSelecionada.getQuantidade();
        this.data = ajusteDeEstoqueSelecionada.getData();
    }

    public AjusteDeEstoqueBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public AjusteDeEstoque construir() throws DadoInvalidoException {
        return new AjusteDeEstoqueBuilder().comObservacao(observacao).comQuantidade(quantidade)
                .comItem(item).comDeposito(deposito).comData(data).construir();
    }

    public AjusteDeEstoque construirComID() throws DadoInvalidoException {
        return new AjusteDeEstoqueBuilder().comID(id).comObservacao(observacao).comQuantidade(quantidade)
                .comItem(item).comDeposito(deposito).comData(data).construir();
    }
}
