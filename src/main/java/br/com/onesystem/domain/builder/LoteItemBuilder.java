/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Cordeiro
 */
public class LoteItemBuilder {

    private Long id;
    private Date dataDeValidade;
    private Date dataDeFabricacao;
    private String lote;
    private boolean ativo;
    private String observacao;
    private Item item;

    public LoteItemBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public LoteItemBuilder comDataDeValidade(Date dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
        return this;
    }

    public LoteItemBuilder comDataDeFabricacao(Date dataDeFabricacao) {
        this.dataDeFabricacao = dataDeFabricacao;
        return this;
    }

    public LoteItemBuilder comLote(String lote) {
        this.lote = lote;
        return this;
    }

    public LoteItemBuilder comAtivo(boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public LoteItemBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public LoteItemBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }
    
    public LoteItem construir() throws DadoInvalidoException {
        return new LoteItem(id, dataDeValidade, dataDeFabricacao, lote, ativo, observacao, item);
    }

}
