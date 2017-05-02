package br.com.onesystem.war.builder;

import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import java.io.Serializable;
import java.math.BigDecimal;

public class QuantidadeDeItemBV implements Serializable {

    private Long id;
    private SaldoDeEstoque saldoDeEstoque;
    private BigDecimal quantidade = BigDecimal.ZERO;

    public QuantidadeDeItemBV() {
    }

    public QuantidadeDeItemBV(Long id, SaldoDeEstoque saldoDeEstoque, BigDecimal quantidade) {
        this.id = id;
        this.saldoDeEstoque = saldoDeEstoque;
        this.quantidade = quantidade;
    }

    public SaldoDeEstoque getSaldoDeEstoque() {
        return saldoDeEstoque;
    }

    public void setSaldoDeEstoque(SaldoDeEstoque saldoDeEstoque) {
        this.saldoDeEstoque = saldoDeEstoque;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QuantidadeDeItemBV{" + "id=" + id + ", saldoDeEstoque=" + saldoDeEstoque + ", quantidade=" + quantidade + '}';
    }

}
