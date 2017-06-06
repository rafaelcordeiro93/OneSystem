package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.builder.ReceitaProvisionadaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceitaProvisionadaBV implements Serializable {

    private Long id;
    private Pessoa pessoa;
    private TipoReceita receita;
    private BigDecimal valor;
    private Date vencimento;
    private Date emissao;
    private String historico;
    private Cotacao cotacao;
    private Date referencia;

    public ReceitaProvisionadaBV(ReceitaProvisionada receitaProvisionadaSelecionada) {
        this.id = receitaProvisionadaSelecionada.getId();
        this.pessoa = receitaProvisionadaSelecionada.getPessoa();
        this.receita = receitaProvisionadaSelecionada.getTipoReceita();
        this.valor = receitaProvisionadaSelecionada.getValor();
        this.vencimento = receitaProvisionadaSelecionada.getVencimento();
        this.emissao = receitaProvisionadaSelecionada.getEmissao();
        this.historico = receitaProvisionadaSelecionada.getHistorico();
        this.cotacao = receitaProvisionadaSelecionada.getCotacao();
        this.referencia = receitaProvisionadaSelecionada.getReferencia();
    }

    public ReceitaProvisionadaBV(Long id, Pessoa pessoa, TipoReceita receita, BigDecimal valor,
            Date vencimento, Date emissao, String historico, Cotacao cotacao, Date referencia) {
        this.id = id;
        this.pessoa = pessoa;
        this.receita = receita;
        this.valor = valor;
        this.vencimento = vencimento;
        this.emissao = emissao;
        this.historico = historico;
        this.cotacao = cotacao;
        this.referencia = referencia;
    }

    public ReceitaProvisionadaBV() {
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

    public Date getReferencia() {
        return referencia;
    }

    public void setReferencia(Date referencia) {
        this.referencia = referencia;
    }

    public ReceitaProvisionada construir() throws DadoInvalidoException {
        return new ReceitaProvisionadaBuilder().comPessoa(pessoa).comReceita(receita)
                .comValor(valor).comVencimento(vencimento).comHistorico(historico).comReferencia(referencia)
                .comCotacao(cotacao).construir();
    }

    public ReceitaProvisionada construirComID() throws DadoInvalidoException {
        return new ReceitaProvisionadaBuilder().comId(id).comPessoa(pessoa).comReceita(receita)
                .comValor(valor).comVencimento(vencimento).comHistorico(historico).comReferencia(referencia)
                .comCotacao(cotacao).construir();
    }
}
