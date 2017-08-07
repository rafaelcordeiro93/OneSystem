package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class OperacaoDeEstoqueBuilder {

    private Long id;
    private ContaDeEstoque contaDeEstoque;
    private Operacao operacoes;
    private OperacaoFisica operacaoFisica;

    public OperacaoDeEstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public OperacaoDeEstoqueBuilder comContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        this.contaDeEstoque = contaDeEstoque;
        return this;
    }

    public OperacaoDeEstoqueBuilder comOperacao(Operacao operacoes) {
        this.operacoes = operacoes;
        return this;
    }

    public OperacaoDeEstoqueBuilder comOperacaoFisica(OperacaoFisica operacaoFisica) {
        this.operacaoFisica = operacaoFisica;
        return this;
    }

    public OperacaoDeEstoque construir() throws DadoInvalidoException {
        return new OperacaoDeEstoque(id, contaDeEstoque, operacoes, operacaoFisica);
    }

}
