package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;

/**
 *
 * @author Rafael
 */
public class ContaDeEstoqueBuilder {

    private Long id;
    private String nome;
    private Operacao operacao;
    private OperacaoFisica operacaoFisica;
    
    public ContaDeEstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ContaDeEstoqueBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContaDeEstoqueBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public ContaDeEstoqueBuilder comOperacaoFisica(OperacaoFisica operacaoFisica) {
        this.operacaoFisica = operacaoFisica;
        return this;
    }

    public ContaDeEstoque construir() throws DadoInvalidoException {
        return new ContaDeEstoque(id, nome, operacao, operacaoFisica);
    }

}
