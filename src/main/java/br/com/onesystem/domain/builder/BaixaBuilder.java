/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.CambioEmpresa;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class BaixaBuilder {

    private Long id;
    private BigDecimal valor;
    private String historico;
    private Date emissao;
    private OperacaoFinanceira naturezaFinanceira;
    private Cotacao cotacao;
    private Cobranca cobranca;
    private TipoDespesa despesa;
    private TipoReceita receita;
    private Pessoa pessoa;
    private Cambio cambio;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private Transferencia transferencia;
    private Recepcao recepcao;
    private EstadoDeBaixa estado;
    private CobrancaFixa cobrancaFixa;
    private ValorPorCotacao valorPorCotacao;
    private TipoDeCobranca tipoDeCobranca;
    private Caixa caixa;
    private FormaDeCobranca formaDeCobranca;
    private DepositoBancario depositoBancario;
    private SaqueBancario saqueBancario;
    private CambioEmpresa cambioEmpresa;

    public BaixaBuilder() {
    }

    public BaixaBuilder(Baixa baixa) {
        this.id = baixa.getId();
        this.historico = baixa.getHistorico();
        this.valor = baixa.getValor();
        this.emissao = baixa.getEmissao();
        this.estado = baixa.getEstado();
        this.naturezaFinanceira = baixa.getNaturezaFinanceira();
        this.cotacao = baixa.getCotacao();
        this.cobranca = baixa.getParcela();
        this.despesa = baixa.getDespesa();
        this.receita = baixa.getReceita();
        this.pessoa = baixa.getPessoa();
        this.cambio = baixa.getCambio();
        this.conhecimentoDeFrete = baixa.getConhecimentoDeFrete();
        this.transferencia = baixa.getTransferencia();
        this.recepcao = baixa.getRecepcao();
        this.cobrancaFixa = baixa.getMovimentoFixo();
        this.valorPorCotacao = baixa.getValorPorCotacao();
        this.tipoDeCobranca = baixa.getTipoDeCobranca();
        this.caixa = baixa.getCaixa();
        this.formaDeCobranca = baixa.getFormaDeCobranca();
    }

    public BaixaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public BaixaBuilder comCaixa(Caixa caixa) {
        this.caixa = caixa;
        return this;
    }

    public BaixaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
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

    public BaixaBuilder comOperacaoFinanceira(OperacaoFinanceira naturezaFinanceira) {
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

    public BaixaBuilder comReceita(TipoReceita receita) {
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

    public BaixaBuilder comCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
        return this;
    }

    public BaixaBuilder comTipoDeCobranca(TipoDeCobranca tipoDeCobranca) {
        this.tipoDeCobranca = tipoDeCobranca;
        return this;
    }

    public BaixaBuilder comFormaDeCobranca(FormaDeCobranca formaDeCobranca) {
        this.formaDeCobranca = formaDeCobranca;
        return this;
    }

    public BaixaBuilder comCobrancaFixa(CobrancaFixa cobrancaFixa) {
        this.cobrancaFixa = cobrancaFixa;
        return this;
    }

    public BaixaBuilder comValorPorCotacao(ValorPorCotacao valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
        return this;
    }

    public BaixaBuilder comDepositoBancario(DepositoBancario depositoBancario) {
        this.depositoBancario = depositoBancario;
        return this;
    }

    public BaixaBuilder comSaqueBancario(SaqueBancario saqueBancario) {
        this.saqueBancario = saqueBancario;
        return this;
    }

    public BaixaBuilder comCambioEmpresa(CambioEmpresa cambioEmpresa) {
        this.cambioEmpresa = cambioEmpresa;
        return this;
    }

    public Baixa construir() throws DadoInvalidoException {
        return new Baixa(id, valor, emissao, historico, naturezaFinanceira, pessoa, despesa, cotacao, receita, cambio, transferencia, recepcao, cobranca, cobrancaFixa, valorPorCotacao, tipoDeCobranca, formaDeCobranca, caixa, depositoBancario, saqueBancario, cambioEmpresa);
    }

}
