/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.war.builder.CobrancaBV;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CobrancaBuilder {

    private Long id;
    private NotaEmitida notaEmitida;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private OperacaoFinanceira operacaoFinanceira;
    private BigDecimal valor;
    private Date emissao;
    private Date vencimento;
    private Banco banco;
    private String agencia;
    private String conta;
    private String numeroCheque;
    private EstadoDeCheque situacaoDeCheque;
    private BigDecimal multas;
    private BigDecimal juros;
    private BigDecimal descontos;
    private String emitente;
    private String observacao;
    private Cartao cartao;
    private String codigoTransacao;
    private SituacaoDeCartao situacaoDeCartao;
    private Moeda moeda;
    private Cambio cambio;
    private Recepcao recepcao;
    private ModalidadeDeCobranca tipoFormaDeRecebimentoParcela;
    private Integer dias;
    private Cotacao cotacao;
    private TipoLancamento tipoLancamento;
    private Pessoa pessoa;
    private Boolean entrada;
    private String historico;
    private FaturaLegada faturaLegada;
    private SituacaoDeCobranca situacaoDeCobranca;
    private Filial filial;
    private Integer parcela;
    private Conta contaBancaria;

    public CobrancaBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public CobrancaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public CobrancaBuilder comNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
        return this;
    }

    public CobrancaBuilder comConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        return this;
    }

    public CobrancaBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public CobrancaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public CobrancaBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public CobrancaBuilder comBanco(Banco banco) {
        this.banco = banco;
        return this;
    }

    public CobrancaBuilder comAgencia(String agencia) {
        this.agencia = agencia;
        return this;
    }

    public CobrancaBuilder comConta(String conta) {
        this.conta = conta;
        return this;
    }

    public CobrancaBuilder comNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
        return this;
    }

    public CobrancaBuilder comSituacaoDeCheque(EstadoDeCheque situacaoDeCheque) {
        this.situacaoDeCheque = situacaoDeCheque;
        return this;
    }

    public CobrancaBuilder comMultas(BigDecimal multas) {
        this.multas = multas;
        return this;
    }

    public CobrancaBuilder comJuros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public CobrancaBuilder comDesconto(BigDecimal descontos) {
        this.descontos = descontos;
        return this;
    }

    public CobrancaBuilder comEmitente(String emitente) {
        this.emitente = emitente;
        return this;
    }

    public CobrancaBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public CobrancaBuilder comCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
        return this;
    }

    public CobrancaBuilder comCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public CobrancaBuilder comSituacaoDeCartao(SituacaoDeCartao situacaoDeCartao) {
        this.situacaoDeCartao = situacaoDeCartao;
        return this;
    }

    public CobrancaBuilder comMoeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public CobrancaBuilder comCambio(Cambio cambio) {
        this.cambio = cambio;
        return this;
    }

    public CobrancaBuilder comRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
        return this;
    }

    public CobrancaBuilder comTipoFormaDeRecebimentoParcela(ModalidadeDeCobranca tipoFormaDeRecebimentoParcela) {
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
        return this;
    }

    public CobrancaBuilder comTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
        return this;
    }

    public CobrancaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public CobrancaBuilder comDias(Integer dias) {
        this.dias = dias;
        return this;
    }

    public CobrancaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public CobrancaBuilder comEntrada(Boolean entrada) {
        this.entrada = entrada;
        return this;
    }

    public CobrancaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public CobrancaBuilder comFaturaLegada(FaturaLegada faturaLegada) {
        this.faturaLegada = faturaLegada;
        return this;
    }

    public CobrancaBuilder comSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca) {
        this.situacaoDeCobranca = situacaoDeCobranca;
        return this;
    }

    public CobrancaBuilder comFilial(Filial filial) {
        this.filial = filial;
        return this;
    }

    public CobrancaBuilder comParcela(Integer parcela) {
        this.parcela = parcela;
        return this;
    }

    public CobrancaBuilder comContaBancaria(Conta contaBancaria) {
        this.contaBancaria = contaBancaria;
        return this;
    }

    public CobrancaBV construir() {
        return new CobrancaBV(id, notaEmitida, conhecimentoDeFrete, operacaoFinanceira, valor, emissao, vencimento, banco, agencia, conta, numeroCheque, situacaoDeCheque, multas, juros, descontos, emitente, observacao, cartao, codigoTransacao, situacaoDeCartao, moeda, cambio, recepcao, tipoFormaDeRecebimentoParcela, dias, cotacao, tipoLancamento, pessoa, entrada, historico, faturaLegada, situacaoDeCobranca, filial, parcela, contaBancaria);
    }

}
