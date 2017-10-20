/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.SituacaoFiscal;
import br.com.onesystem.domain.builder.ItemDeNotaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.MoedaFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ItemDeNotaBV {
    
    private Long id;
    private Item item;
    private BigDecimal unitario;
    private Nota nota;
    private List<QuantidadeDeItemPorDeposito> listaDeQuantidade = new ArrayList<QuantidadeDeItemPorDeposito>();
    private BigDecimal quantidade;
    private BigDecimal iva;
    private SituacaoFiscal situacaoFiscal;
    private Long cfop;
    private BigDecimal valorTotalIva;
    private LoteItem loteItem;
    
    public ItemDeNotaBV() {
    }
    
    public ItemDeNotaBV(ItemDeNota i) {
        this.id = i.getId();
        this.item = i.getItem();
        this.nota = i.getNota();
        this.unitario = i.getUnitario();
        this.quantidade = i.getQuantidade();
        this.listaDeQuantidade = i.getListaDeQuantidade();
        this.iva = i.getIva();
        this.situacaoFiscal = i.getSituacaoFiscal();
        this.cfop = i.getCfop();
        this.valorTotalIva = i.getValorTotalIva();
        this.loteItem = i.getLoteItem();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Item getItem() {
        return item;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public BigDecimal getUnitario() {
        return unitario;
    }
    
    public void setUnitario(BigDecimal unitario) {
        this.unitario = unitario;
    }
    
    public Nota getNota() {
        return nota;
    }
    
    public void setNota(Nota nota) {
        this.nota = nota;
    }
    
    public BigDecimal getIva() {
        return iva;
    }
    
    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }
    
    public SituacaoFiscal getSituacaoFiscal() {
        return situacaoFiscal;
    }
    
    public void setSituacaoFiscal(SituacaoFiscal situacaoFiscal) {
        this.situacaoFiscal = situacaoFiscal;
    }
    
    public Long getCfop() {
        return cfop;
    }
    
    public void setCfop(Long cfop) {
        this.cfop = cfop;
    }
    
    public BigDecimal getValorTotalIva() {
        return valorTotalIva;
    }
    
    public void setValorTotalIva(BigDecimal valorTotalIva) {
        this.valorTotalIva = valorTotalIva;
    }
    
    public List<QuantidadeDeItemPorDeposito> getListaDeQuantidade() {
        return listaDeQuantidade;
    }
    
    public void setListaDeQuantidade(List<QuantidadeDeItemPorDeposito> listaDeQuantidade) {
        this.listaDeQuantidade = listaDeQuantidade;
    }
    
    public BigDecimal getTotalListaSaldoDeQuantidade() {
        return getQuantidade().subtract(getListaDeQuantidade().stream().map(q -> q.getSaldoDeEstoque().getSaldo()).reduce(BigDecimal.ZERO, BigDecimal::add));
    }
    
    public BigDecimal getTotalListaDeQuantidade() {
        return getListaDeQuantidade().stream().map(QuantidadeDeItemPorDeposito::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public String getValorTotalListaDeQuantidadeFormatado() {
        BigDecimal resultado = getUnitario().multiply(getListaDeQuantidade().stream().map(QuantidadeDeItemPorDeposito::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add));
        return MoedaFormatter.format(nota.getCotacao().getConta().getMoeda(), resultado);
    }
    
    public int getComparaQuantidadeDevolucao() {
        BigDecimal r = getQuantidade().subtract(getTotalListaSaldoDeQuantidade());
        return r.compareTo(getTotalListaDeQuantidade());
    }
    
    public BigDecimal getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
    
    public BigDecimal getTotal() {
        return getQuantidade() == null ? BigDecimal.ZERO : getQuantidade().multiply(unitario == null ? BigDecimal.ZERO : unitario);
    }
    
    public String getTotalFormatado() {
        if (nota != null) {
            return MoedaFormatter.format(nota.getCotacao().getConta().getMoeda(), getTotal());
        } else {
            return NumberFormat.getNumberInstance().format(getTotal());
        }
    }
    
    public String getUnitarioFormatado() {
        if (nota != null) {
            return MoedaFormatter.format(nota.getCotacao().getConta().getMoeda(), getUnitario());
        } else {
            return NumberFormat.getNumberInstance().format(getUnitario());
        }
    }
    
    public LoteItem getLoteItem() {
        return loteItem;
    }
    
    public void setLoteItem(LoteItem loteItem) {
        this.loteItem = loteItem;
    }
    
    public ItemDeNota construir() throws DadoInvalidoException {
        return new ItemDeNotaBuilder().comItem(item).comUnitario(unitario).comListaDeQuantidade(listaDeQuantidade).comIva(iva)
                .comValorIva(valorTotalIva).comCfop(cfop).comSituacaoFiscal(situacaoFiscal).comLoteItem(loteItem).construir();
    }
    
    public ItemDeNota construirComId() throws DadoInvalidoException {
        return new ItemDeNotaBuilder().comId(id).comItem(item).comUnitario(unitario).comListaDeQuantidade(listaDeQuantidade).comIva(iva)
                .comValorIva(valorTotalIva).comCfop(cfop).comSituacaoFiscal(situacaoFiscal).comLoteItem(loteItem).construir();
    }
    
}
