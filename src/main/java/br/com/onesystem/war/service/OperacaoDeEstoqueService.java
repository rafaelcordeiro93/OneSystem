package br.com.onesystem.war.service;

import br.com.onesystem.dao.OperacaoDeEstoqueDAO;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import java.io.Serializable;
import java.util.List;

public class OperacaoDeEstoqueService implements Serializable {
    
    public List<OperacaoDeEstoque> buscarOperacoesDeEstoquePor(Operacao operacao){
        return new OperacaoDeEstoqueDAO().porOperacao(operacao).listaDeResultados();
    }
        
}
