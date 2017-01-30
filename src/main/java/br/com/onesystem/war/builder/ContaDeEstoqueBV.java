package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.builder.ContaDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class ContaDeEstoqueBV implements Serializable {

    private Long id;
    private String nome;
    private List<Operacao> operacao = new ArrayList<Operacao>();
    private OperacaoFisica operacaoFisica;

    public ContaDeEstoqueBV(ContaDeEstoque contaDeEstoqueSelecionada) {
        this.id = contaDeEstoqueSelecionada.getId();
        this.nome = contaDeEstoqueSelecionada.getNome();
        this.operacao = contaDeEstoqueSelecionada.getOperacoes();
        this.operacaoFisica = contaDeEstoqueSelecionada.getOperacaoFisica();
    }

    public ContaDeEstoqueBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Operacao> getOperacao() {
        return operacao;
    }

    public void setOperacao(List<Operacao> operacao) {
        this.operacao = operacao;
    }

    public OperacaoFisica getOperacaoFisica() {
        return operacaoFisica;
    }

    public void setOperacaoFisica(OperacaoFisica operacaoFisica) {
        this.operacaoFisica = operacaoFisica;
    }

    public ContaDeEstoque construir() throws DadoInvalidoException {
        return new ContaDeEstoqueBuilder().comNome(nome).comOperacoes(operacao).comOperacaoFisica(operacaoFisica).construir();
    }

    public ContaDeEstoque construirComID() throws DadoInvalidoException {
        return new ContaDeEstoqueBuilder().comID(id).comNome(nome).comOperacoes(operacao).comOperacaoFisica(operacaoFisica).construir();
    }
}
