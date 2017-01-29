package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ContaDeEstoqueBuilder {

    private Long id;
    private String nome;
    private List<Operacao> operacoes;
    private OperacaoFisica operacaoFisica;

    public ContaDeEstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ContaDeEstoqueBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContaDeEstoqueBuilder comOperacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
        return this;
    }

    public ContaDeEstoqueBuilder comOperacaoFisica(OperacaoFisica operacaoFisica) {
        this.operacaoFisica = operacaoFisica;
        return this;
    }

    public ContaDeEstoque construir() throws DadoInvalidoException {
        return new ContaDeEstoque(id, nome, operacaoFisica, operacoes);
    }

}
