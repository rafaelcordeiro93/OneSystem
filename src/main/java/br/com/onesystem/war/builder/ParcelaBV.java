/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.util.DateUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimento;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ParcelaBV implements Serializable {

    private Long id;
    private NotaEmitida notaEmitida;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private OperacaoFinanceira unidadeFinanceira;
    private Integer numeroParcela;
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
    private BoletoDeCartao boletoDeCartao;
    private Integer dias;
    private String codTransacao;
    private SituacaoDeCartao tipoSituacaoCartao;
    private Moeda moeda;
    private Cambio cambio;
    private Recepcao recepcao;
    private TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela;
    private String diaDaSemana;

    public ParcelaBV() {
    }

    public ParcelaBV(Long id, NotaEmitida notaEmitida, ConhecimentoDeFrete conhecimentoDeFrete, 
            OperacaoFinanceira unidadeFinanceira, Integer numeroParcela, BigDecimal valor, 
            Date emissao, Date vencimento, Banco banco, String agencia, String conta, 
            String numeroCheque, SituacaoDeCheque situacaoDeCheque, BigDecimal multas, 
            BigDecimal juros, BigDecimal descontos, String emitente, String observacao, 
            BoletoDeCartao boletoDeCartao, Integer dias, String codTransacao, 
            SituacaoDeCartao tipoSituacaoCartao, Moeda moeda, Cambio cambio, 
            Recepcao recepcao, TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela) {
        this.id = id;
        this.notaEmitida = notaEmitida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        this.unidadeFinanceira = unidadeFinanceira;
        this.numeroParcela = numeroParcela;
        this.valor = valor;
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCheque = numeroCheque;
        this.situacaoDeCheque = situacaoDeCheque;
        this.multas = multas;
        this.juros = juros;
        this.descontos = descontos;
        this.emitente = emitente;
        this.observacao = observacao;
        this.boletoDeCartao = boletoDeCartao;
        this.dias = dias;
        this.codTransacao = codTransacao;
        this.tipoSituacaoCartao = tipoSituacaoCartao;
        this.moeda = moeda;
        this.cambio = cambio;
        this.recepcao = recepcao;
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    public OperacaoFinanceira getUnidadeFinanceira() {
        return unidadeFinanceira;
    }

    public void setUnidadeFinanceira(OperacaoFinanceira unidadeFinanceira) {
        this.unidadeFinanceira = unidadeFinanceira;
    }

    public Integer getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(Integer numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getVencimento() {
        return vencimento;
    }
    
    public String getVencimentoFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(vencimento);
    }

    public String getDiaDaSemana() {
        diaDaSemana = new DateUtil().getDiaDaSemana(vencimento);
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }
    
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public SituacaoDeCheque getSituacaoDeCheque() {
        return situacaoDeCheque;
    }

    public void setSituacaoDeCheque(SituacaoDeCheque situacaoDeCheque) {
        this.situacaoDeCheque = situacaoDeCheque;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public void setMultas(BigDecimal multas) {
        this.multas = multas;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getDescontos() {
        return descontos;
    }

    public void setDescontos(BigDecimal descontos) {
        this.descontos = descontos;
    }

    public String getEmitente() {
        return emitente;
    }

    public void setEmitente(String emitente) {
        this.emitente = emitente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public BoletoDeCartao getBoletoDeCartao() {
        return boletoDeCartao;
    }

    public void setBoletoDeCartao(BoletoDeCartao boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
    }

    public Integer getDias() {
        Long diasCalculados = new DateUtil().getDifererencaDeDiasEntreDatas(vencimento, new Date());
        dias = diasCalculados.intValue();
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getCodTransacao() {
        return codTransacao;
    }

    public void setCodTransacao(String codTransacao) {
        this.codTransacao = codTransacao;
    }

    public SituacaoDeCartao getTipoSituacaoCartao() {
        return tipoSituacaoCartao;
    }

    public void setTipoSituacaoCartao(SituacaoDeCartao tipoSituacaoCartao) {
        this.tipoSituacaoCartao = tipoSituacaoCartao;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public TipoFormaDeRecebimentoParcela getTipoFormaDeRecebimentoParcela() {
        return tipoFormaDeRecebimentoParcela;
    }

    public void setTipoFormaDeRecebimentoParcela(TipoFormaDeRecebimentoParcela tipoFormaDeRecebimentoParcela) {
        this.tipoFormaDeRecebimentoParcela = tipoFormaDeRecebimentoParcela;
    }
   
}
