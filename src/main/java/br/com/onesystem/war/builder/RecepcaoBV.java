package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.RecepcaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RecepcaoBV implements Serializable {

    private Long id;
    private Conta conta;
    private Pessoa pessoa;
    private BigDecimal valor;
    private Date emissao = new Date();
    private Titulo titulo;
    private Cotacao cotacao;

    public RecepcaoBV(Recepcao recepcaoSelecionada) {
        this.id = recepcaoSelecionada.getId();
        this.pessoa = recepcaoSelecionada.getPessoa();
        this.valor = recepcaoSelecionada.getValor();
        this.emissao = recepcaoSelecionada.getEmissao();
        this.titulo = recepcaoSelecionada.getTitulo();
        this.cotacao = recepcaoSelecionada.getCotacao();
    }

    public RecepcaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
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

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public Recepcao construir() throws DadoInvalidoException {
        return new RecepcaoBuilder().comPessoa(pessoa).comValor(valor)
                .comEmissao(emissao).comTitulo(titulo).comCotacao(cotacao).construir();
    }

    public Recepcao construirComID() throws DadoInvalidoException {
        return new RecepcaoBuilder().comId(id).comPessoa(pessoa).comValor(valor)
                .comEmissao(emissao).comTitulo(titulo).comCotacao(cotacao).construir();
    }
}
