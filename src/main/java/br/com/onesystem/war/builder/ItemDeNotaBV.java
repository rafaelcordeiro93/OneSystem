/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.builder.ItemDeNotaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.util.MoedaFormatter;
import com.sun.javafx.scene.control.skin.VirtualFlow;
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

    public ItemDeNotaBV() {
    }

    public ItemDeNotaBV(ItemDeNota itemEmitidoSelecionado) {
        this.id = itemEmitidoSelecionado.getId();
        this.item = itemEmitidoSelecionado.getItem();
        this.nota = itemEmitidoSelecionado.getNota();
        this.unitario = itemEmitidoSelecionado.getUnitario();
        this.quantidade = itemEmitidoSelecionado.getQuantidade();
        this.listaDeQuantidade = itemEmitidoSelecionado.getListaDeQuantidade();
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

    public ItemDeNota construir() throws DadoInvalidoException {
        return new ItemDeNotaBuilder().comItem(item).comNota(nota).comUnitario(unitario).comListaDeQuantidade(listaDeQuantidade).construir();
    }

    public ItemDeNota construirComId() throws DadoInvalidoException {
        return new ItemDeNotaBuilder().comId(id).comItem(item).comNota(nota).comUnitario(unitario).comListaDeQuantidade(listaDeQuantidade).construir();
    }

}
