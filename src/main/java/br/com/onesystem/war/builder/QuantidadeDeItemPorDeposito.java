package br.com.onesystem.war.builder;

import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import java.io.Serializable;
import java.math.BigDecimal;

public class QuantidadeDeItemPorDeposito implements Serializable {

    private Long id;
    private SaldoDeEstoque saldoDeEstoque;
    private BigDecimal quantidade = BigDecimal.ZERO;

    public QuantidadeDeItemPorDeposito() {
    }

    public QuantidadeDeItemPorDeposito(Long id, SaldoDeEstoque saldoDeEstoque, BigDecimal quantidade) {
        this.id = id;
        this.saldoDeEstoque = saldoDeEstoque;
        this.quantidade = quantidade;
    }

    public SaldoDeEstoque getSaldoDeEstoque() {
        return saldoDeEstoque;
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

}
