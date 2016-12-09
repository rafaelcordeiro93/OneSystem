package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConhecimentoDeFreteBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private BigDecimal valorFrete;
    private BigDecimal outrasdespesas;
    private Date data;
    private Date emissao;
    private Moeda moeda;
    private Conta conta;

    public ConhecimentoDeFreteBV(ConhecimentoDeFrete conhecimentoDeFreteSelecionado) {
        this.id = conhecimentoDeFreteSelecionado.getId();
        this.pessoa = conhecimentoDeFreteSelecionado.getPessoa();
        this.operacao = conhecimentoDeFreteSelecionado.getOperacao();
        this.valorFrete = conhecimentoDeFreteSelecionado.getValorFrete();
        this.outrasdespesas = conhecimentoDeFreteSelecionado.getOutrasdespesas();
        this.data = conhecimentoDeFreteSelecionado.getData();
        this.emissao = conhecimentoDeFreteSelecionado.getEmissao();
        this.moeda = conhecimentoDeFreteSelecionado.getMoeda();
        this.conta = conhecimentoDeFreteSelecionado.getConta();

    }

    public ConhecimentoDeFreteBV(Long id, Pessoa pessoa, Operacao operacao, BigDecimal valorFrete, BigDecimal outrasdespesas,
            Date data, Date emissao, Moeda moeda, Conta conta) {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.valorFrete = valorFrete;
        this.outrasdespesas = outrasdespesas;
        this.data = data;
        this.emissao = emissao;
        this.moeda = moeda;
        this.conta = conta;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public BigDecimal getOutrasdespesas() {
        return outrasdespesas;
    }

    public void setOutrasdespesas(BigDecimal outrasdespesas) {
        this.outrasdespesas = outrasdespesas;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    

    public ConhecimentoDeFreteBV() {
    }

    public ConhecimentoDeFrete construir() throws DadoInvalidoException {
        return new ConhecimentoDeFrete(null, pessoa, operacao, valorFrete, outrasdespesas, data, emissao, moeda, conta);
    }

    public ConhecimentoDeFrete construirComID() throws DadoInvalidoException {
        return new ConhecimentoDeFrete(id, pessoa, operacao, valorFrete, outrasdespesas, data, emissao, moeda, conta);
    }
}
