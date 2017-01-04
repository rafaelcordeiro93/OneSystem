/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Cotacao;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CotacaoValores {

    private Long id;
    private Cotacao cotacao;
    private BigDecimal valorAReceber;
    private BigDecimal total;
    private BigDecimal totalConvertidoRecebido;

    public CotacaoValores(Cotacao cotacao, BigDecimal valorAReceber, BigDecimal total, BigDecimal totalConvertidoRecebido) {
        this.id = cotacao.getId();
        this.cotacao = cotacao;
        this.valorAReceber = valorAReceber;
        this.total = total;
        this.totalConvertidoRecebido = totalConvertidoRecebido;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public BigDecimal getValorAReceber() {
        return valorAReceber;
    }

    public Long getId() {
        return id;
    }

    public void setValorAReceber(BigDecimal valorAReceber) {
        this.valorAReceber = valorAReceber;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setTotalConvertidoRecebido(BigDecimal totalConvertidoRecebido) {
        this.totalConvertidoRecebido = totalConvertidoRecebido;
    }

    public BigDecimal getValorConvertidoRestante() {
        if (total != null && totalConvertidoRecebido != null && cotacao.getValor() != null) {
            return (total.multiply(cotacao.getValor())).subtract(totalConvertidoRecebido.multiply(cotacao.getValor()));
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getValorRestante() {
        return getValorConvertidoRestante().divide(cotacao.getValor(), 2, BigDecimal.ROUND_UP);
    }

    /**
     * @return Retorna o valorAReceber que está sendo recebido convertido para a
     * moeda padrão. Para fazer o cálculo é feito a divisão do valorAReceber
     * proposto que o cliente está pagando, pelo valorAReceber da cotação da
     * moeda.
     */
    public BigDecimal getValorConvertidoRecebido() {
        if (valorAReceber == null || valorAReceber == BigDecimal.ZERO) {
            return BigDecimal.ZERO;
        } else {
            return valorAReceber.divide(cotacao.getValor(), 16, BigDecimal.ROUND_UP);
        }
    }

}
