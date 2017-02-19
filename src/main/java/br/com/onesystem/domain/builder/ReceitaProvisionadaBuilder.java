package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.ReceitaProvisionada;
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
public class ReceitaProvisionadaBuilder {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private Cambio cambio;
    private List<Baixa> baixas;
    private Cotacao cotacao;
    private Receita receita;
    private boolean divisaoLucroCambioCaixa;

    public ReceitaProvisionadaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ReceitaProvisionadaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public ReceitaProvisionadaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public ReceitaProvisionadaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ReceitaProvisionadaBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public ReceitaProvisionadaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public ReceitaProvisionadaBuilder comReceita(Receita receita) {
        this.receita = receita;
        return this;
    }

    public ReceitaProvisionadaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public ReceitaProvisionadaBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public ReceitaProvisionada construir() throws DadoInvalidoException {
        return new ReceitaProvisionada(id, pessoa, receita, valor, vencimento, emissao, historico, cotacao, baixas);
    }

}
