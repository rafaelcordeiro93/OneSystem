package br.com.onesystem.war.builder;

import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.builder.LoteItemBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class LoteItemBV implements Serializable, BuilderView<LoteItem> {

    private Long id;
    private Date dataDeValidade;
    private Date dataDeFabricacao;
    private Long numeroDoLote;
    private boolean ativo = true;
    private String observacao;
    private Item item;
    private BigDecimal saldo;

    public LoteItemBV(LoteItem n) {
        this.id = n.getId();
        this.dataDeValidade = n.getDataDeValidade();
        this.dataDeFabricacao = n.getDataDeFabricacao();
        this.numeroDoLote = n.getNumeroDoLote();
        this.ativo = n.isAtivo();
        this.observacao = n.getObservacao();
        this.item = n.getItem();
        this.saldo = n.getSaldo();
    }

    public LoteItemBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(Date dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }

    public Date getDataDeFabricacao() {
        return dataDeFabricacao;
    }

    public void setDataDeFabricacao(Date dataDeFabricacao) {
        this.dataDeFabricacao = dataDeFabricacao;
    }

    public Long getNumeroDoLote() {
        return numeroDoLote;
    }

    public void setNumeroDoLote(Long numeroDoLote) {
        this.numeroDoLote = numeroDoLote;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public LoteItem construir() throws DadoInvalidoException {
        return new LoteItemBuilder().comDataDeValidade(dataDeValidade).comDataDeFabricacao(dataDeFabricacao).comAtivo(ativo)
                .comNumeroDoLote(numeroDoLote).comObservacao(observacao).comItem(item).comSaldo(saldo).construir();
    }

    public LoteItem construirComID() throws DadoInvalidoException {
        return new LoteItemBuilder().comId(id).comDataDeValidade(dataDeValidade).comDataDeFabricacao(dataDeFabricacao).comAtivo(ativo)
                .comNumeroDoLote(numeroDoLote).comObservacao(observacao).comItem(item).comSaldo(saldo).construir();
    }
}
