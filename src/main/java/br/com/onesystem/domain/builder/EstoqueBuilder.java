package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class EstoqueBuilder {

    private Long id;
    private Deposito deposito;
    private Item item;
    private BigDecimal saldo;
    private OperacaoDeEstoque operacaoDeEstoque;
    private Date emissao = new Date();
    private ItemDeNota itemDeNota;
    private AjusteDeEstoque ajusteDeEstoque;
    private ItemDeCondicional itemDeCondicional;
    private boolean cancelado;

    public EstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public EstoqueBuilder comDeposito(Deposito deposito) {
        this.deposito = deposito;
        return this;
    }

    public EstoqueBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public EstoqueBuilder comQuantidade(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public EstoqueBuilder comOperacaoDeEstoque(OperacaoDeEstoque operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
        return this;
    }

    public EstoqueBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public EstoqueBuilder comItemDeNota(ItemDeNota itemDeNota) {
        this.itemDeNota = itemDeNota;
        return this;
    }

    public EstoqueBuilder comItemDeCondicional(ItemDeCondicional itemDeCondicional) {
        this.itemDeCondicional = itemDeCondicional;
        return this;
    }

    public EstoqueBuilder comAjusteDeEstoque(AjusteDeEstoque ajusteDeEstoque) {
        this.ajusteDeEstoque = ajusteDeEstoque;
        return this;
    }

    public EstoqueBuilder comCancelado(boolean cancelado) {
        this.cancelado = cancelado;
        return this;
    }

    public Estoque construir() throws DadoInvalidoException {
        return new Estoque(id, item, saldo, deposito, emissao, itemDeNota, ajusteDeEstoque, operacaoDeEstoque, itemDeCondicional, cancelado);
    }

}
