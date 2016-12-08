package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class ConhecimentoDeFreteBuilder {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private BigDecimal valorFrete;
    private BigDecimal outrasdespesas;
    private Date data;
    private Date emissao;
    private Moeda moeda;
    private Conta conta;

    public ConhecimentoDeFreteBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ConhecimentoDeFreteBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public ConhecimentoDeFreteBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public ConhecimentoDeFreteBuilder comValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
        return this;
    }

    public ConhecimentoDeFreteBuilder comOutrasDespesas(BigDecimal outrasdespesas) {
        this.outrasdespesas = outrasdespesas;
        return this;
    }

    public ConhecimentoDeFreteBuilder comData(Date data) {
        this.data = data;
        return this;
    }

    public ConhecimentoDeFreteBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public ConhecimentoDeFreteBuilder comMoeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public ConhecimentoDeFreteBuilder comConta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public ConhecimentoDeFrete construir() throws DadoInvalidoException {
        return new ConhecimentoDeFrete(id, pessoa, operacao, valorFrete, outrasdespesas, data, emissao, moeda, conta);
    }

}
