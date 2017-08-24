package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.OperacaoDeEstoqueBV;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ContaDeEstoqueBuilder {

    private Long id;
    private String nome;
    private List<OperacaoDeEstoque> operacoesDeEstoque;

    public ContaDeEstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ContaDeEstoqueBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContaDeEstoqueBuilder comOperacaoDeEstoque(List<OperacaoDeEstoque> operacoesDeEstoque) {
        this.operacoesDeEstoque = operacoesDeEstoque;
        return this;
    }

    public ContaDeEstoque construir() throws DadoInvalidoException {
        return new ContaDeEstoque(id, operacoesDeEstoque, nome);
    }

}
