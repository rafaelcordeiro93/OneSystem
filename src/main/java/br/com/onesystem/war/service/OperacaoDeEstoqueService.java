package br.com.onesystem.war.service;

import br.com.onesystem.dao.OperacaoDeEstoqueDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class OperacaoDeEstoqueService implements Serializable {

    @Inject
    private OperacaoDeEstoqueDAO dao;

    public List<OperacaoDeEstoque> buscarOperacoesDeEstoquePor(Operacao operacao) {
        return dao.porOperacao(operacao).listaDeResultados();
    }

    public List<OperacaoDeEstoque> buscarOperacoesDeEstoquePor(ContaDeEstoque contaDeEstoque) {
        return dao.porContaDeEstoque(contaDeEstoque).listaDeResultados();
    }

    public OperacaoFisica buscarOperacaoFisicaPor(Operacao operacao) {
        for (OperacaoDeEstoque e : dao.porOperacao(operacao).listaDeResultados()) {            
                return e.getOperacaoFisica();            
        }
        return null;
    }

}
