package br.com.onesystem.war.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EstoqueBV implements Serializable {

    private Long id;
    private Deposito deposito;
    private Item item;
    private BigDecimal quantidade;
    private OperacaoDeEstoque operacaoDeEstoque;
    private Date emissao;
    private ItemDeNota itemEmitido;
    private AjusteDeEstoque ajusteDeEstoque;

    public EstoqueBV(Estoque estoqueSelecionado) {
        this.id = estoqueSelecionado.getId();
        this.deposito = estoqueSelecionado.getDeposito();
        this.item = estoqueSelecionado.getItem();
        this.quantidade = estoqueSelecionado.getQuantidade();
        this.emissao = estoqueSelecionado.getEmissao();
        this.itemEmitido = estoqueSelecionado.getItemDeNota();
        this.ajusteDeEstoque = estoqueSelecionado.getAjusteDeEstoque();
        this.operacaoDeEstoque = estoqueSelecionado.getOperacaoDeEstoque();
    }

    public EstoqueBV(Deposito deposito, BigDecimal quantidade, Item item, OperacaoDeEstoque operacaoDeEstoque,
            ItemDeNota itemEmitido, Date emissao, AjusteDeEstoque ajusteDeEstoque) {
        this.deposito = deposito;
        this.quantidade = quantidade;
        this.item = item;
        this.operacaoDeEstoque = operacaoDeEstoque;
        this.itemEmitido = itemEmitido;
        this.emissao = emissao;
        this.ajusteDeEstoque = ajusteDeEstoque;
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

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public ItemDeNota getItemEmitido() {
        return itemEmitido;
    }

    public void setItemEmitido(ItemDeNota itemEmitido) {
        this.itemEmitido = itemEmitido;
    }

    public Estoque construir() throws DadoInvalidoException {
        return new EstoqueBuilder().comQuantidade(quantidade).comItemDeNota(itemEmitido)
                .comAjusteDeEstoque(ajusteDeEstoque).comEmissao(emissao)
                .comItem(item).comDeposito(deposito).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }

    public Estoque construirComID() throws DadoInvalidoException {
        return new EstoqueBuilder().comQuantidade(quantidade).comItemDeNota(itemEmitido)
                .comAjusteDeEstoque(ajusteDeEstoque).comEmissao(emissao).comID(id)
                .comItem(item).comDeposito(deposito).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }
}
