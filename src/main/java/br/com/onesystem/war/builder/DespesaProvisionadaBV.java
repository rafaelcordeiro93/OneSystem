package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.DespesaProvisionadaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DespesaProvisionadaBV implements Serializable, BuilderView<DespesaProvisionada> {

    private Long id;
    private Pessoa pessoa;
    private TipoDespesa despesa;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao;
    private String historico;
    private boolean divisaoLucroCambioCaixa;
    private Cotacao cotacao;
    private OperacaoFinanceira operacaoFinanceira;
    private Cambio cambio;
    private Date referencia;

    public DespesaProvisionadaBV(DespesaProvisionada despesaProvisionadaSelecionada) {
        this.id = despesaProvisionadaSelecionada.getId();
        this.pessoa = despesaProvisionadaSelecionada.getPessoa();
        this.despesa = despesaProvisionadaSelecionada.getTipoDespesa();
        this.valor = despesaProvisionadaSelecionada.getValor();
        this.vencimento = despesaProvisionadaSelecionada.getVencimento();
        this.emissao = despesaProvisionadaSelecionada.getEmissao();
        this.historico = despesaProvisionadaSelecionada.getHistorico();
        this.divisaoLucroCambioCaixa = despesaProvisionadaSelecionada.isDivisaoLucroCambioCaixa();
        this.cotacao = despesaProvisionadaSelecionada.getCotacao();
        this.cambio = despesaProvisionadaSelecionada.getCambio();
        this.referencia = despesaProvisionadaSelecionada.getReferencia();
        this.operacaoFinanceira = despesaProvisionadaSelecionada.getOperacaoFinanceira();
    }

    public DespesaProvisionadaBV(Long id, Pessoa pessoa, TipoDespesa despesa, BigDecimal valor, Date vencimento,
            Date emissao, String historico, boolean divisaoLucroCambioCaixa, Cotacao cotacao,
            Cambio cambio, List<Baixa> baixas, Date referencia, OperacaoFinanceira operacaoFinanceira) {
        this.id = id;
        this.pessoa = pessoa;
        this.despesa = despesa;
        this.valor = valor;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.historico = historico;
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
        this.cotacao = cotacao;
        this.cambio = cambio;
        this.referencia = referencia;
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public DespesaProvisionadaBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public void setDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(vencimento);
    }
    
    public String getReferenciaFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        return sdf.format(referencia);
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public boolean isDivisaoLucroCambioCaixa() {
        return divisaoLucroCambioCaixa;
    }

    public void setDivisaoLucroCambioCaixa(boolean divisaoLucroCambioCaixa) {
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Date getReferencia() {
        return referencia;
    }

    public void setReferencia(Date referencia) {
        this.referencia = referencia;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public DespesaProvisionada construir() throws DadoInvalidoException {
        return new DespesaProvisionadaBuilder().comPessoa(pessoa).comValor(valor).comVencimento(vencimento)
                .comDespesa(despesa).comCambio(cambio).comReferencia(referencia)
                .comEmissao(emissao).comHistorico(historico).comCotacao(cotacao).comOperacaoFinanceira(operacaoFinanceira).construir();

    }

    public DespesaProvisionada construirComID() throws DadoInvalidoException {
        return new DespesaProvisionadaBuilder().comId(id).comPessoa(pessoa).comValor(valor).comVencimento(vencimento)
                .comDespesa(despesa).comCambio(cambio).comReferencia(referencia)
                .comEmissao(emissao).comHistorico(historico).comCotacao(cotacao).comOperacaoFinanceira(operacaoFinanceira).construir();
    }
}
