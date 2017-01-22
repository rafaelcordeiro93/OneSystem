package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.DateUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CotacaoValoresBV implements Serializable {

    private Long id;
    private Date data = DateUtil.getCurrentDateTime();
    private Moeda moeda;
    private BigDecimal valor;
    private Conta conta;

    public CotacaoValoresBV(Cotacao cotacaoSelecionada) {
        this.id = cotacaoSelecionada.getId();
        this.data = cotacaoSelecionada.getEmissao();
        this.moeda = cotacaoSelecionada.getMoeda();
        this.valor = cotacaoSelecionada.getValor();
        this.conta = cotacaoSelecionada.getConta();
    }

    public CotacaoValoresBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Cotacao construir() throws DadoInvalidoException {
        return new Cotacao(null, moeda, valor, data, conta);
    }

    public Cotacao construirComID() throws DadoInvalidoException {
        return new Cotacao(id, moeda, valor, data, conta);
    }
}
