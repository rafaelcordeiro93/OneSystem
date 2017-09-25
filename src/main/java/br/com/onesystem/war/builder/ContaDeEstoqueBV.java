package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.ContaDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class ContaDeEstoqueBV implements Serializable, BuilderView<ContaDeEstoque> {

    private Long id;
    private String nome;
    private List<OperacaoDeEstoque> operacoesDeEstoque = new ArrayList<OperacaoDeEstoque>();

    public ContaDeEstoqueBV(ContaDeEstoque contaDeEstoqueSelecionada) {
        this.id = contaDeEstoqueSelecionada.getId();
        this.nome = contaDeEstoqueSelecionada.getNome();
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

    public List<OperacaoDeEstoque> getOperacoesDeEstoque() {
        return operacoesDeEstoque;
    }

    public void setOperacoesDeEstoque(List<OperacaoDeEstoque> operacoesDeEstoque) {
        this.operacoesDeEstoque = operacoesDeEstoque;
    }

    public ContaDeEstoque construir() throws DadoInvalidoException {
        return new ContaDeEstoqueBuilder().comNome(nome).comOperacaoDeEstoque(operacoesDeEstoque).construir();
    }

    public ContaDeEstoque construirComID() throws DadoInvalidoException {
        return new ContaDeEstoqueBuilder().comID(id).comOperacaoDeEstoque(operacoesDeEstoque).comNome(nome).construir();
    }
}
