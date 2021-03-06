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
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.domain.Movimento;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class BaixaBuilder {

    private Long id;
    private BigDecimal valor;
    private String historico;
    private Date emissao;
    private Date dataCompensacao;
    private OperacaoFinanceira operacaoFinanceira;
    private NaturezaFinanceira naturezaFinanceira;
    private Cotacao cotacao;
    private TipoDespesa despesa;
    private TipoReceita receita;
    private Pessoa pessoa;
    private Cambio cambio;
    private Transferencia transferencia;
    private Recepcao recepcao;
    private EstadoDeBaixa estado;
    private ValorPorCotacao valorPorCotacao;
    private TipoDeCobranca tipoDeCobranca;
    private Caixa caixa;
    private FormaDeCobranca formaDeCobranca;
    private DepositoBancario depositoBancario;
    private SaqueBancario saqueBancario;
    private CambioEmpresa cambioEmpresa;
    private LancamentoBancario lancamentoBancario;
    private Filial filial;
    private Movimento movimento;

    public BaixaBuilder() {
    }

    public BaixaBuilder(Baixa baixa) {
        this.id = baixa.getId();
        this.historico = baixa.getHistorico();
        this.valor = baixa.getValor();
        this.emissao = baixa.getEmissao();
        this.dataCompensacao = baixa.getDataCompensacao();
        this.estado = baixa.getEstado();
        this.operacaoFinanceira = baixa.getOperacaoFinanceira();
        this.cotacao = baixa.getCotacao();
        this.despesa = baixa.getDespesa();
        this.receita = baixa.getReceita();
        this.pessoa = baixa.getPessoa();
        this.cambio = baixa.getCambio();
        this.transferencia = baixa.getTransferencia();
        this.recepcao = baixa.getRecepcao();
        this.valorPorCotacao = baixa.getValorPorCotacao();
        this.tipoDeCobranca = baixa.getTipoDeCobranca();
        this.caixa = baixa.getCaixa();
        this.formaDeCobranca = baixa.getFormaDeCobranca();
        this.lancamentoBancario = baixa.getLancamentoBancario();
        this.filial = filial;
        this.movimento = movimento;
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

    public BaixaBuilder comDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
        return this;
    }

    public BaixaBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
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

    public BaixaBuilder comTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
        return this;
    }

    public BaixaBuilder comRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
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

    public BaixaBuilder comLancamentoBancario(LancamentoBancario lancamentoBancario) {
        this.lancamentoBancario = lancamentoBancario;
        return this;
    }

    public BaixaBuilder comCambioEmpresa(CambioEmpresa cambioEmpresa) {
        this.cambioEmpresa = cambioEmpresa;
        return this;
    }

    public BaixaBuilder comEstadoDeBaixa(EstadoDeBaixa estado) {
        this.estado = estado;
        return this;
    }

    public BaixaBuilder comFilial(Filial filial) {
        this.filial = filial;
        return this;
    }
    
    public BaixaBuilder comMovimento(Movimento movimento){
        this.movimento = movimento;
        return this;
    }

    public Baixa construir() throws DadoInvalidoException {
        return new Baixa(id, valor, emissao, dataCompensacao, historico, operacaoFinanceira, pessoa, despesa, cotacao, receita, cambio, transferencia,
                recepcao, valorPorCotacao, tipoDeCobranca, formaDeCobranca, caixa, depositoBancario, saqueBancario, lancamentoBancario, cambioEmpresa, estado, filial, movimento);
    }

}
