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
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FaturaLegada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
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
public class ParcelaBuilder {

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
    private SituacaoDeCheque situacaoDeCheque;
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

    public ParcelaBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ParcelaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ParcelaBuilder comNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
        return this;
    }

    public ParcelaBuilder comConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        return this;
    }

    public ParcelaBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public ParcelaBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public ParcelaBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }

    public ParcelaBuilder comBanco(Banco banco) {
        this.banco = banco;
        return this;
    }

    public ParcelaBuilder comAgencia(String agencia) {
        this.agencia = agencia;
        return this;
    }

    public ParcelaBuilder comConta(String conta) {
        this.conta = conta;
        return this;
    }

    public ParcelaBuilder comNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
        return this;
    }

    public ParcelaBuilder comSituacaoDeCheque(SituacaoDeCheque situacaoDeCheque) {
        this.situacaoDeCheque = situacaoDeCheque;
        return this;
    }

    public ParcelaBuilder comMultas(BigDecimal multas) {
        this.multas = multas;
        return this;
    }

    public ParcelaBuilder comJuros(BigDecimal juros) {
        this.juros = juros;
        return this;
    }

    public ParcelaBuilder comDesconto(BigDecimal descontos) {
        this.descontos = descontos;
        return this;
    }

    public ParcelaBuilder comEmitente(String emitente) {
        this.emitente = emitente;
        return this;
    }

    public ParcelaBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }

    public ParcelaBuilder comCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
        return this;
    }

    public ParcelaBuilder comCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public ParcelaBuilder comSituacaoDeCartao(SituacaoDeCartao situacaoDeCartao) {
        this.situacaoDeCartao = situacaoDeCartao;
        return this;
    }

    public ParcelaBuilder comMoeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public ParcelaBuilder comCambio(Cambio cambio) {
        this.cambio = cambio;
        return this;
    }

    public ParcelaBuilder comRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
        return this;
    }

    public ParcelaBuilder comTipoFormaDeRecebimentoParcela(ModalidadeDeCobranca tipoFormaDeRecebimentoParcela) {
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
        return this;
    }

    public ParcelaBuilder comTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
        return this;
    }

    public ParcelaBuilder comCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
        return this;
    }

    public ParcelaBuilder comDias(Integer dias) {
        this.dias = dias;
        return this;
    }

    public ParcelaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public ParcelaBuilder comEntrada(Boolean entrada) {
        this.entrada = entrada;
        return this;
    }

    public ParcelaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public ParcelaBuilder comFaturaLegada(FaturaLegada faturaLegada) {
        this.faturaLegada = faturaLegada;
        return this;
    }

    public ParcelaBuilder comSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca) {
        this.situacaoDeCobranca = situacaoDeCobranca;
        return this;
    }
    
    public CobrancaBV construir() {
        return new CobrancaBV(id, notaEmitida, conhecimentoDeFrete, operacaoFinanceira, valor, emissao, vencimento, banco, agencia, conta, numeroCheque, situacaoDeCheque, multas, juros, descontos, emitente, observacao, cartao, codigoTransacao, situacaoDeCartao, moeda, cambio, recepcao, tipoFormaDeRecebimentoParcela, dias, cotacao, tipoLancamento, pessoa, entrada, historico, faturaLegada, situacaoDeCobranca);
    }


}
