package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemEmitido;
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
    private BigDecimal quantidade;
    private OperacaoFisica tipo;
    private Date emissao = new Date();
    private ItemEmitido itemEmitido;

    public EstoqueBV(Estoque estoqueSelecionado) {
        this.id = estoqueSelecionado.getId();
        this.deposito = estoqueSelecionado.getDeposito();
        this.item = estoqueSelecionado.getItem();
        this.quantidade = estoqueSelecionado.getQuantidade();
        this.tipo = estoqueSelecionado.getOperacaoFisica();
        this.emissao = estoqueSelecionado.getEmissao();
        this.itemEmitido = itemEmitido;
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

    public ItemEmitido getItemEmitido() {
        return itemEmitido;
    }

    public void setItemEmitido(ItemEmitido itemEmitido) {
        this.itemEmitido = itemEmitido;
    }
    
    

    public Estoque construir() throws DadoInvalidoException {
        return new EstoqueBuilder().comSaldo(quantidade)
                .comItem(item).comDeposito(deposito).comOperacaoFisica(tipo).construir();
    }

    public Estoque construirComID() throws DadoInvalidoException {
        return new EstoqueBuilder().comID(id).comSaldo(quantidade)
                .comItem(item).comDeposito(deposito).comOperacaoFisica(tipo).construir();
    }
}
