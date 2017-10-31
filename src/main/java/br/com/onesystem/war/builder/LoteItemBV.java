package br.com.onesystem.war.builder;

import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.builder.LoteItemBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.util.Date;

public class LoteItemBV implements Serializable, BuilderView<LoteItem> {

    private Long id;
    private Date dataDeValidade = new Date();
    private Date dataDeFabricacao = new Date();
    private String lote;
    private boolean ativo = true;
    private String observacao;
    private Item item;

    public LoteItemBV(LoteItem n) {
        this.id = n.getId();
        this.dataDeValidade = n.getDataDeValidade();
        this.dataDeFabricacao = n.getDataDeFabricacao();
        this.lote = n.getLote();
        this.ativo = n.isAtivo();
        this.observacao = n.getObservacao();
        this.item = n.getItem();
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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        if (lote != null) {
            lote = lote.trim();
        }
        this.lote = lote;
    }

    public LoteItem construir() throws DadoInvalidoException {
        return new LoteItemBuilder().comDataDeValidade(dataDeValidade).comDataDeFabricacao(dataDeFabricacao).comAtivo(ativo)
                .comLote(lote).comObservacao(observacao).comItem(item).construir();
    }

    public LoteItem construirComID() throws DadoInvalidoException {
        return new LoteItemBuilder().comId(id).comDataDeValidade(dataDeValidade).comDataDeFabricacao(dataDeFabricacao).comAtivo(ativo)
                .comLote(lote).comObservacao(observacao).comItem(item).construir();
    }
}
