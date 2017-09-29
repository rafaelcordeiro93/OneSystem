package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.builder.CreditoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreditoBV implements Serializable, BuilderView<Credito> {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private OperacaoFinanceira operacaoFinanceira;
    private Nota nota;
    private Cotacao cotacao;
    private Boolean entrada;
    private Filial filial;

    public CreditoBV() {
    }

    public CreditoBV(Credito c) {
        this.id = c.getId();
        this.emissao = c.getEmissao();
        this.pessoa = c.getPessoa();
        this.valor = c.getValor();
        this.nota = c.getNota();
        this.historico = c.getHistorico();
        this.vencimento = c.getVencimento();
        this.operacaoFinanceira = c.getOperacaoFinanceira();
        this.cotacao = c.getCotacao();
        this.entrada = c.getEntrada();
        this.filial = c.getFilial();
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

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
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

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public Credito construir() throws DadoInvalidoException {
        return new CreditoBuilder().comCotacao(cotacao).comEmissao(emissao).comEntrada(entrada)
                .comHistorico(historico).comNota(nota).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa)
                .comValor(valor).comVencimento(vencimento).comFilial(filial).construir();
    }

    public Credito construirComID() throws DadoInvalidoException {
        return new CreditoBuilder().comId(id).comCotacao(cotacao).comEmissao(emissao).comEntrada(entrada)
                .comHistorico(historico).comNota(nota).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa)
                .comValor(valor).comVencimento(vencimento).comFilial(filial).construir();
    }

}
