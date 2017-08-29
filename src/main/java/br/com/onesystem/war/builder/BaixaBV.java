package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.CobrancaFixa;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.builder.BaixaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class BaixaBV implements Serializable {

    private Long id;
    private BigDecimal valor = BigDecimal.ZERO;
    private String historico = "";
    private Date emissao = Calendar.getInstance().getTime();
    private Date dataCompensacao;
    private OperacaoFinanceira operacaoFinanceira;
    private NaturezaFinanceira naturezaFinanceira;
    private Cotacao cotacao;
    private Cobranca cobranca;
    private TipoDespesa despesa;
    private TipoReceita receita;
    private Pessoa pessoa;
    private Cambio cambio;
    private Transferencia transferencia;
    private DepositoBancario depositoBancario;
    private LancamentoBancario lancamentoBancario;
    private Recepcao recepcao;
    private EstadoDeBaixa estado;
    private Date dataCancelamento;
    private ValorPorCotacao valorPorCotacao;
    private TipoDeCobranca tipoDeCobranca;
    private Caixa caixa;
    private Filial filial;

    public BaixaBV() {
    }

    public BaixaBV(Baixa baixa) {
        this.id = baixa.getId();
        this.pessoa = baixa.getPessoa();
        this.estado = baixa.getEstado();
        this.dataCancelamento = baixa.getDataCancelamento();
        this.valor = baixa.getValor();
        this.emissao = baixa.getEmissao();
        this.dataCompensacao = baixa.getDataCompensacao();
        this.historico = baixa.getHistorico();
        this.operacaoFinanceira = baixa.getOperacaoFinanceira();
        this.naturezaFinanceira = baixa.getNaturezaFinanceira();
        this.despesa = baixa.getDespesa();
        this.cambio = baixa.getCambio();
        this.recepcao = baixa.getRecepcao();
        this.cobranca = baixa.getCobranca();
        this.receita = baixa.getReceita();
        this.transferencia = baixa.getTransferencia();
        this.cotacao = baixa.getCotacao();
        this.valorPorCotacao = baixa.getValorPorCotacao();
        this.lancamentoBancario = baixa.getLancamentoBancario();
        this.depositoBancario = baixa.getDepositoBancario();
        this.tipoDeCobranca = baixa.getTipoDeCobranca();
        this.caixa = baixa.getCaixa();
        this.filial = baixa.getFilial();
    }

    public void selecionaTitulo(Titulo titulo) {
        this.pessoa = titulo.getPessoa();
        this.emissao = titulo.getEmissao();
        this.historico = titulo.getHistorico();
        this.operacaoFinanceira = titulo.getOperacaoFinanceira();
        this.recepcao = titulo.getRecepcao();
        this.cobranca = titulo;
        this.valor = titulo.getSaldo();
        this.cambio = titulo.getCambio();
        this.filial = titulo.getFilial();
    }

    public void selecionaDespesaProvisionada(DespesaProvisionada despesaProvisionada) {
        this.pessoa = despesaProvisionada.getPessoa();
        this.valor = despesaProvisionada.getValor();
        this.emissao = despesaProvisionada.getEmissao();
        this.historico = despesaProvisionada.getHistorico();
        this.cobranca = despesaProvisionada;
        this.despesa = despesaProvisionada.getTipoDespesa();
        this.cambio = despesaProvisionada.getCambio();
        this.filial = despesaProvisionada.getFilial();
    }

    public void selecionaReceitaProvisionada(ReceitaProvisionada receitaProvisionada) {
        this.pessoa = receitaProvisionada.getPessoa();
        this.valor = receitaProvisionada.getValor();
        this.emissao = receitaProvisionada.getEmissao();
        this.historico = receitaProvisionada.getHistorico();
        this.cobranca = receitaProvisionada;
        this.filial = receitaProvisionada.getFilial();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getDataCompensacao() {
        return dataCompensacao;
    }

    public void setDataCompensacao(Date dataCompensacao) {
        this.dataCompensacao = dataCompensacao;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public void setDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public TipoReceita getReceita() {
        return receita;
    }

    public void setReceita(TipoReceita receita) {
        this.receita = receita;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public EstadoDeBaixa getEstado() {
        return estado;
    }

    public void setEstado(EstadoDeBaixa estado) {
        this.estado = estado;
    }

    public ValorPorCotacao getValorPorCotacao() {
        return valorPorCotacao;
    }

    public void setValorPorCotacao(ValorPorCotacao valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
    }

    public DepositoBancario getDepositoBancario() {
        return depositoBancario;
    }

    public void setDepositoBancario(DepositoBancario depositoBancario) {
        this.depositoBancario = depositoBancario;
    }

    public LancamentoBancario getLancamentoBancario() {
        return lancamentoBancario;
    }

    public void setLancamentoBancario(LancamentoBancario lancamentoBancario) {
        this.lancamentoBancario = lancamentoBancario;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public NaturezaFinanceira getNaturezaFinanceira() {
        return naturezaFinanceira;
    }

    public void setNaturezaFinanceira(NaturezaFinanceira naturezaFinanceira) {
        this.naturezaFinanceira = naturezaFinanceira;
    }

    public TipoDeCobranca getTipoDeCobranca() {
        return tipoDeCobranca;
    }

    public void setTipoDeCobranca(TipoDeCobranca tipoDeCobranca) {
        this.tipoDeCobranca = tipoDeCobranca;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }
    
    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Baixa construir() throws DadoInvalidoException {
        return new BaixaBuilder().comCambio(cambio)
                .comCotacao(cotacao).comDespesa(despesa).comEmissao(emissao).comEstadoDeBaixa(estado).comDataCompensacao(dataCompensacao).comTipoDeCobranca(tipoDeCobranca).comCaixa(caixa)
                .comHistorico(historico).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa).comReceita(receita).comRecepcao(recepcao).comCobranca(cobranca)
                .comTransferencia(transferencia).comValor(valor).comDepositoBancario(depositoBancario).comLancamentoBancario(lancamentoBancario).comValorPorCotacao(valorPorCotacao)
                .comFilial(filial).construir();
    }

    public Baixa construirComID() throws DadoInvalidoException {
        return new BaixaBuilder().comCambio(cambio)
                .comCotacao(cotacao).comDespesa(despesa).comId(id).comCaixa(caixa)
                .comEmissao(emissao).comHistorico(historico).comTipoDeCobranca(tipoDeCobranca)
                .comOperacaoFinanceira(operacaoFinanceira).comEstadoDeBaixa(estado).comDataCompensacao(dataCompensacao)
                .comPessoa(pessoa).comReceita(receita).comRecepcao(recepcao).comCobranca(cobranca)
                .comTransferencia(transferencia).comValor(valor).comDepositoBancario(depositoBancario).comLancamentoBancario(lancamentoBancario)
                .comFilial(filial).comValorPorCotacao(valorPorCotacao).construir();
    }

}
