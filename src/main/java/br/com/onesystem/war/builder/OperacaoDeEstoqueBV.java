package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.OperacaoDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;

public class OperacaoDeEstoqueBV implements Serializable {

    private Long id;
    private ContaDeEstoque contaDeEstoque;
    private Operacao operacao;
    private OperacaoFisica operacaoFisica;

    public OperacaoDeEstoqueBV(OperacaoDeEstoque operacaoDeEstoqueSelecionada) {
        this.id = operacaoDeEstoqueSelecionada.getId();
        this.contaDeEstoque = operacaoDeEstoqueSelecionada.getContaDeEstoque();
        this.operacao = operacaoDeEstoqueSelecionada.getOperacao();
        this.operacaoFisica = operacaoDeEstoqueSelecionada.getOperacaoFisica();
    }
    
    public OperacaoDeEstoqueBV(OperacaoDeEstoqueBV operacaoDeEstoqueSelecionada) {
        this.id = operacaoDeEstoqueSelecionada.getId();
        this.contaDeEstoque = operacaoDeEstoqueSelecionada.getContaDeEstoque();
        this.operacao = operacaoDeEstoqueSelecionada.getOperacao();
        this.operacaoFisica = operacaoDeEstoqueSelecionada.getOperacaoFisica();
    }

    public OperacaoDeEstoqueBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContaDeEstoque getContaDeEstoque() {
        return contaDeEstoque;
    }

    public void setContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        this.contaDeEstoque = contaDeEstoque;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    public OperacaoFisica getOperacaoFisica() {
        return operacaoFisica;
    }

    public void setOperacaoFisica(OperacaoFisica operacaoFisica) {
        this.operacaoFisica = operacaoFisica;
    }

    public OperacaoDeEstoque construir() throws DadoInvalidoException {
        return new OperacaoDeEstoqueBuilder().comContaDeEstoque(contaDeEstoque).comOperacao(operacao).comOperacaoFisica(operacaoFisica).construir();
    }
    
    public OperacaoDeEstoque construirComID() throws DadoInvalidoException {
        return new OperacaoDeEstoqueBuilder().comID(id).comContaDeEstoque(contaDeEstoque).comOperacao(operacao).comOperacaoFisica(operacaoFisica).construir();
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof OperacaoDeEstoqueBV)) {
            return false;
        }
        OperacaoDeEstoqueBV outro = (OperacaoDeEstoqueBV) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
