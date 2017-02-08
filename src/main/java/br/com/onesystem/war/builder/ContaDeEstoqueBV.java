package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.ContaDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class ContaDeEstoqueBV implements Serializable {

    private Long id;
    private String nome;
    private List<OperacaoDeEstoque> operacaoDeEstoque = new ArrayList<OperacaoDeEstoque>();

    public ContaDeEstoqueBV(ContaDeEstoque contaDeEstoqueSelecionada) {
        this.id = contaDeEstoqueSelecionada.getId();
        this.nome = contaDeEstoqueSelecionada.getNome();
        this.operacaoDeEstoque = contaDeEstoqueSelecionada.getOperacaoDeEstoque();

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

    public List<OperacaoDeEstoque> getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    public void setOperacaoDeEstoque(List<OperacaoDeEstoque> operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
    }

    public ContaDeEstoque construir() throws DadoInvalidoException {
        return new ContaDeEstoqueBuilder().comNome(nome).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }

    public ContaDeEstoque construirComID() throws DadoInvalidoException {
        return new ContaDeEstoqueBuilder().comID(id).comNome(nome).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }
}
