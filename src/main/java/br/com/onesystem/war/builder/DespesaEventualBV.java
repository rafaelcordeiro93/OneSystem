package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaEventual;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.DespesaEventualBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DespesaEventualBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private TipoDespesa despesa;
    private BigDecimal valor;
    private Date vencimento;
    private OperacaoFinanceira operacaoFinanceira;
    private Date emissao;
    private String historico;
    private Cotacao cotacao;
    private Date referencia;
    private Filial filial;

    public DespesaEventualBV(DespesaEventual despesaEventualSelecionada) {
        this.id = despesaEventualSelecionada.getId();
        this.pessoa = despesaEventualSelecionada.getPessoa();
        this.despesa = despesaEventualSelecionada.getTipoDespesa();
        this.valor = despesaEventualSelecionada.getValor();
        this.vencimento = despesaEventualSelecionada.getVencimento();
        this.emissao = despesaEventualSelecionada.getEmissao();
        this.historico = despesaEventualSelecionada.getHistorico();
        this.cotacao = despesaEventualSelecionada.getCotacao();
        this.operacaoFinanceira = despesaEventualSelecionada.getOperacaoFinanceira();
        this.referencia = despesaEventualSelecionada.getReferencia();
        this.filial = despesaEventualSelecionada.getFilial();
    }

    public DespesaEventualBV(Long id, Pessoa pessoa, TipoDespesa despesa, BigDecimal valor, Date vencimento,
            Date emissao, String historico, Cotacao cotacao, List<Baixa> baixas, OperacaoFinanceira operacaoFinanceira,
            Date referencia, Filial filial) {
        this.id = id;
        this.pessoa = pessoa;
        this.despesa = despesa;
        this.valor = valor;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.historico = historico;
        this.cotacao = cotacao;
        this.operacaoFinanceira = operacaoFinanceira;
        this.referencia = referencia;
        this.filial = filial;
    }

    public DespesaEventualBV() {
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

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
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

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
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
    
    public DespesaEventual construir() throws DadoInvalidoException {
        return new DespesaEventualBuilder().comPessoa(pessoa).comValor(valor).comVencimento(vencimento).comOperacaoFinanceira(operacaoFinanceira)
                .comDespesa(despesa).comEmissao(emissao).comHistorico(historico).comCotacao(cotacao)
                .comReferencia(referencia).comFilial(filial).construir();

    }

    public DespesaEventual construirComID() throws DadoInvalidoException {
        return new DespesaEventualBuilder().comId(id).comPessoa(pessoa).comValor(valor).comVencimento(vencimento).comOperacaoFinanceira(operacaoFinanceira)
                .comDespesa(despesa).comEmissao(emissao).comHistorico(historico).comCotacao(cotacao)
                .comReferencia(referencia).comFilial(filial).construir();
    }
}
