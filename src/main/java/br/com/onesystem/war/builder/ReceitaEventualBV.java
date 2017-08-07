package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaEventual;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.ReceitaEventualBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceitaEventualBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private TipoReceita receita;
    private OperacaoFinanceira operacaoFinanceira;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao;
    private String historico;
    private Cotacao cotacao;
    private List<Baixa> baixas;
    private Date referencia;

    public ReceitaEventualBV(ReceitaEventual receitaEventualSelecionada) {
        this.id = receitaEventualSelecionada.getId();
        this.pessoa = receitaEventualSelecionada.getPessoa();
        this.receita = receitaEventualSelecionada.getTipoReceita();
        this.valor = receitaEventualSelecionada.getValor();
        this.vencimento = receitaEventualSelecionada.getVencimento();
        this.emissao = receitaEventualSelecionada.getEmissao();
        this.historico = receitaEventualSelecionada.getHistorico();
        this.cotacao = receitaEventualSelecionada.getCotacao();
        this.baixas = receitaEventualSelecionada.getBaixas();
        this.operacaoFinanceira = receitaEventualSelecionada.getOperacaoFinanceira();
        this.referencia = receitaEventualSelecionada.getReferencia();
    }

    public ReceitaEventualBV(Long id, Pessoa pessoa, TipoReceita receita, BigDecimal valor, Date vencimento,
            Date emissao, String historico, Cotacao cotacao, List<Baixa> baixas, OperacaoFinanceira operacaoFinanceira,
            Date referencia) {
        this.id = id;
        this.pessoa = pessoa;
        this.receita = receita;
        this.valor = valor;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.historico = historico;
        this.cotacao = cotacao;
        this.baixas = baixas;
        this.operacaoFinanceira = operacaoFinanceira;
        this.referencia = referencia;
    }

    public ReceitaEventualBV() {
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

    public TipoReceita getReceita() {
        return receita;
    }

    public void setReceita(TipoReceita receita) {
        this.receita = receita;
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

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public Date getReferencia() {
        return referencia;
    } 

    public void setReferencia(Date referencia) {
        this.referencia = referencia;
    }

    public ReceitaEventual construir() throws DadoInvalidoException {
        return new ReceitaEventualBuilder().comPessoa(pessoa).comValor(valor).comOperacaoFinanceira(operacaoFinanceira)
                .comReceita(receita).comBaixas(baixas).comEmissao(emissao).comHistorico(historico).comCotacao(cotacao)
                .comReferencia(referencia).construir();

    }

    public ReceitaEventual construirComID() throws DadoInvalidoException {
        return new ReceitaEventualBuilder().comId(id).comPessoa(pessoa).comValor(valor).comOperacaoFinanceira(operacaoFinanceira)
                .comReceita(receita).comBaixas(baixas).comEmissao(emissao).comHistorico(historico).comCotacao(cotacao)
                .comReferencia(referencia).construir();
    }
}
