package br.com.onesystem.dao;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class OperacaoDeEstoqueDAO extends GenericDAO<OperacaoDeEstoque> {

    public OperacaoDeEstoqueDAO() {
        super(OperacaoDeEstoque.class);
    }

    public OperacaoDeEstoqueDAO porOperacao(Operacao operacao) {
        where += " and operacaoDeEstoque.operacao = :oOperacao ";
        parametros.put("oOperacao", operacao);
        return this;
    }

    public OperacaoDeEstoqueDAO porContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        where += " and operacaoDeEstoque.contaDeEstoque = :oContaDeEstoque ";
        parametros.put("oContaDeEstoque", contaDeEstoque);
        return this;
    }

}
