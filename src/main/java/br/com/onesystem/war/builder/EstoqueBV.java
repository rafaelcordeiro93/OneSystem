package br.com.onesystem.war.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.LoteItem;
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
    private LoteItem lote;
    private ItemDeCondicional itemCondicional;
    private ContaDeEstoque contaDeEstoque;
    private boolean cancelado;

    public EstoqueBV(Estoque e) {
        this.id = e.getId();
        this.deposito = e.getDeposito();
        this.item = e.getItem();
        this.quantidade = e.getQuantidade();
        this.emissao = e.getEmissao();
        this.itemEmitido = e.getItemDeNota();
        this.ajusteDeEstoque = e.getAjusteDeEstoque();
        this.operacaoDeEstoque = e.getOperacaoDeEstoque();
        this.lote = e.getLoteItem();
        this.itemCondicional = e.getItemDeCondicional();
        this.contaDeEstoque = e.getContaDeEstoque();
        this.cancelado = e.isCancelado();
    }

    public EstoqueBV(Long id, Deposito deposito, BigDecimal quantidade, Item item, OperacaoDeEstoque operacaoDeEstoque,
            ItemDeNota itemEmitido, Date emissao, AjusteDeEstoque ajusteDeEstoque, LoteItem lote,
            ItemDeCondicional itemCondicional, ContaDeEstoque contaDeEstoque, boolean cancelado) {
        this.deposito = deposito;
        this.quantidade = quantidade;
        this.item = item;
        this.operacaoDeEstoque = operacaoDeEstoque;
        this.itemEmitido = itemEmitido;
        this.emissao = emissao;
        this.ajusteDeEstoque = ajusteDeEstoque;
        this.id = id;
        this.lote = lote;
        this.itemCondicional = itemCondicional;
        this.contaDeEstoque = contaDeEstoque;
        this.cancelado = cancelado;
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

    public OperacaoDeEstoque getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    public void setOperacaoDeEstoque(OperacaoDeEstoque operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
    }

    public AjusteDeEstoque getAjusteDeEstoque() {
        return ajusteDeEstoque;
    }

    public void setAjusteDeEstoque(AjusteDeEstoque ajusteDeEstoque) {
        this.ajusteDeEstoque = ajusteDeEstoque;
    }

    public LoteItem getLote() {
        return lote;
    }

    public void setLote(LoteItem lote) {
        this.lote = lote;
    }

    public ItemDeCondicional getItemCondicional() {
        return itemCondicional;
    }

    public void setItemCondicional(ItemDeCondicional itemCondicional) {
        this.itemCondicional = itemCondicional;
    }

    public ContaDeEstoque getContaDeEstoque() {
        return contaDeEstoque;
    }

    public void setContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        this.contaDeEstoque = contaDeEstoque;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public Estoque construir() throws DadoInvalidoException {
        return new EstoqueBuilder().comQuantidade(quantidade).comItemDeNota(itemEmitido)
                .comAjusteDeEstoque(ajusteDeEstoque).comEmissao(emissao).comLoteItem(lote)
                .comItemDeCondicional(itemCondicional).comCancelado(cancelado).comContaDeEstoque(contaDeEstoque)
                .comItem(item).comDeposito(deposito).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }

    public Estoque construirComID() throws DadoInvalidoException {
        return new EstoqueBuilder().comID(id).comQuantidade(quantidade).comItemDeNota(itemEmitido)
                .comAjusteDeEstoque(ajusteDeEstoque).comEmissao(emissao).comLoteItem(lote)
                .comItemDeCondicional(itemCondicional).comCancelado(cancelado).comContaDeEstoque(contaDeEstoque)
                .comItem(item).comDeposito(deposito).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }
}
