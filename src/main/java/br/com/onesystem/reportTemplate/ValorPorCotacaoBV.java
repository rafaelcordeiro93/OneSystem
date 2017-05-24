/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.builder.ValorPorCotacaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.Objects;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ValorPorCotacaoBV {
    
    private Long id;
    private Cotacao cotacao;
    private BigDecimal valorAReceber;
    private BigDecimal total;
    private BigDecimal totalConvertidoRecebido;
    private Moeda moedaPadrao;
    
    public ValorPorCotacaoBV(ValorPorCotacao v) {
        this.id = v.getId();
        this.cotacao = v.getCotacao();
        this.valorAReceber = v.getValor();
    }
    
    public ValorPorCotacaoBV(Cotacao cotacao, BigDecimal valorAReceber, BigDecimal total, BigDecimal totalConvertidoRecebido, Moeda moedaPadrao) {
        this.id = cotacao.getId();
        this.cotacao = cotacao;
        this.valorAReceber = valorAReceber;
        this.total = total;
        this.totalConvertidoRecebido = totalConvertidoRecebido;
        this.moedaPadrao = moedaPadrao;
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
            valorNaMoeda = valorNaMoeda.setScale(14, BigDecimal.ROUND_HALF_EVEN);
            BigDecimal totalC = total.multiply(cotacao.getValor());
            BigDecimal subtract = (totalC).subtract(valorNaMoeda);
            subtract = subtract.setScale(2, BigDecimal.ROUND_HALF_EVEN);
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
            return NumberFormat.getCurrencyInstance(moedaPadrao.getBandeira().getLocal()).format(BigDecimal.ZERO);
        } else {
            return NumberFormat.getCurrencyInstance(moedaPadrao.getBandeira().getLocal()).format(valorAReceber.divide(cotacao.getValor(), 2, BigDecimal.ROUND_UP));
        }
    }
    
    public ValorPorCotacao construir() throws DadoInvalidoException {
        return new ValorPorCotacaoBuilder().comCotacao(cotacao).comValor(valorAReceber).construir();
    }
    
    public ValorPorCotacao construirComId() throws DadoInvalidoException {
        return new ValorPorCotacaoBuilder().comId(id).comCotacao(cotacao).comValor(valorAReceber).construir();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ValorPorCotacaoBV other = (ValorPorCotacaoBV) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
