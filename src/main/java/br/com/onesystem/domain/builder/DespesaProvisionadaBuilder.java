package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class DespesaProvisionadaBuilder {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private Cambio cambio;
    private List<Baixa> baixas;
    private Cotacao cotacao;
    private TipoDespesa despesa;
    private boolean divisaoLucroCambioCaixa;

    public DespesaProvisionadaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public DespesaProvisionadaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public DespesaProvisionadaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public DespesaProvisionadaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public DespesaProvisionadaBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public DespesaProvisionadaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public DespesaProvisionadaBuilder comDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
        return this;
    }

    public DespesaProvisionadaBuilder comCambio(Cambio cambio) {
        this.cambio = cambio;
        return this;
    }

    public DespesaProvisionadaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public DespesaProvisionadaBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public DespesaProvisionadaBuilder comDivisaoLucroCambioCaixa(boolean divisaoLucroCambioCaixa) {
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
        return this;
    }

    public DespesaProvisionada construir() throws DadoInvalidoException {
        return new DespesaProvisionada(id, pessoa, despesa, valor, vencimento, emissao, historico, cambio, divisaoLucroCambioCaixa, cotacao, baixas);
    }

}
