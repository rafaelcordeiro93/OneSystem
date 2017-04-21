/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Transacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class BaixaBuilder {

    private Long id;
    private Integer numeroParcela;
    private BigDecimal juros = BigDecimal.ZERO;
    private BigDecimal valor = BigDecimal.ZERO;
    private BigDecimal multas = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;
    private String historico = "";
    private Date emissao = Calendar.getInstance().getTime();
    private OperacaoFinanceira naturezaFinanceira;
    private Cotacao cotacao;
    private Transacao perfilDeValor;
    private TipoDespesa despesa;
    private Receita receita;
    private Pessoa pessoa;
    private Cambio cambio;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private Transferencia transferencia;
    private Recepcao recepcao;
    private boolean cancelada = false;
    private NotaEmitida notaEmitida;

    public BaixaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public BaixaBuilder comNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
        return this;
    }

    public BaixaBuilder comJuros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public BaixaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public BaixaBuilder comMultas(BigDecimal multas) {
        this.multas = multas;
        return this;
    }

    public BaixaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public BaixaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public BaixaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public BaixaBuilder comNaturezaFinanceira(OperacaoFinanceira naturezaFinanceira) {
        this.naturezaFinanceira = naturezaFinanceira;
        return this;
    }

    public BaixaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public BaixaBuilder comDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
        return this;
    }

    public BaixaBuilder comReceita(Receita receita) {
        this.receita = receita;
        return this;
    }

    public BaixaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public BaixaBuilder comCambio(Cambio cambio) {
        this.cambio = cambio;
        return this;
    }

    public BaixaBuilder comConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        return this;
    }

    public BaixaBuilder comTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
        return this;
    }

    public BaixaBuilder comRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
        return this;
    }

    public BaixaBuilder cancelada(boolean cancelada) {
        this.cancelada = cancelada;
        return this;
    }

    public BaixaBuilder comNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
        return this;
    }

    public BaixaBuilder comPerfilDeValor(Transacao perfilDeValor) {
        this.perfilDeValor = perfilDeValor;
        return this;
    }

    public Baixa construir() throws DadoInvalidoException {
        return new Baixa(id, numeroParcela, cancelada, juros, valor, multas, desconto, emissao, historico, naturezaFinanceira, pessoa, despesa, cotacao, receita, cambio, transferencia, recepcao, perfilDeValor, notaEmitida);
    }

}
