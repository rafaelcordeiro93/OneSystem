/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

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
        return valorAReceber == null ? BigDecimal.ZERO : valorAReceber;
    }

    public String getValorAReceberFormatado() {
        return NumberFormat.getCurrencyInstance(cotacao.getConta().getMoeda().getBandeira().getLocal()).format(getValorAReceber());
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

    public String getValorConvertidoRestanteFormatado() {
        return NumberFormat.getCurrencyInstance(cotacao.getConta().getMoeda().getBandeira().getLocal()).format(getValorConvertidoRestante());
    }

    public BigDecimal getValorConvertidoRestante() {
        if (total != null && totalConvertidoRecebido != null && cotacao.getValor() != null) {
            BigDecimal valorNaMoeda = totalConvertidoRecebido.multiply(cotacao.getValor(), MathContext.DECIMAL64);
            valorNaMoeda = valorNaMoeda.setScale(14, RoundingMode.HALF_DOWN);
            BigDecimal totalC = total.multiply(cotacao.getValor());
            BigDecimal subtract = (totalC).subtract(valorNaMoeda);
            subtract = subtract.setScale(2, BigDecimal.ROUND_UP);
            return subtract;
        } else {
            return BigDecimal.ZERO;
        }
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
            return valorAReceber.divide(cotacao.getValor(), 14, BigDecimal.ROUND_UP);
        }
    }

    public String getValorConvertidoRecebidoView() {
        if (valorAReceber == null || valorAReceber == BigDecimal.ZERO) {
            return NumberFormat.getCurrencyInstance(cotacao.getConta().getMoeda().getBandeira().getLocal()).format(BigDecimal.ZERO);
        } else {
            return NumberFormat.getCurrencyInstance(cotacao.getConta().getMoeda().getBandeira().getLocal()).format(valorAReceber.divide(cotacao.getValor(), 2, BigDecimal.ROUND_UP));
        }
    }

}
