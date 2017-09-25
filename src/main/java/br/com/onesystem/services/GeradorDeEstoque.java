/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import br.com.onesystem.war.service.OperacaoDeEstoqueService;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * Deve gerar os estoques das operacoes efetuadas no sistema. Toda geracao de
 * estoque deve estar centralizada nessa classe especialista, em virtude de
 * maior facilidade em manutencao.
 *
 * @date 25/09/2017
 * @author Rafael
 */
public class GeradorDeEstoque implements Serializable{

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    public void geraEstoqueDe(Nota nota) throws DadoInvalidoException {
        if (nota instanceof NotaEmitida) {
            NotaEmitida notaEmitida = (NotaEmitida) nota;
            if (notaEmitida.getCondicional() == null || (notaEmitida.getCondicional() != null && !(notaEmitida.getCondicional().getOperacao().getOperacaoDeEstoque().equals(notaEmitida.getOperacao().getOperacaoDeEstoque())))) {
                geraEstoqueDeNota(nota);
            }
        }else{
            geraEstoqueDe(nota);
        }

    }

    private void geraEstoqueDeNota(Nota nota) throws DadoInvalidoException {
        List<OperacaoDeEstoque> listaDeOperacoes = operacaoDeEstoqueService.buscarOperacoesDeEstoquePor(nota.getOperacao());
        for (ItemDeNota in : nota.getItens()) {
            for (QuantidadeDeItemPorDeposito q : in.getListaDeQuantidade()) {

                for (OperacaoDeEstoque operacaoDeEstoque : listaDeOperacoes) {
                    Estoque e = new EstoqueBuilder().comDeposito(q.getSaldoDeEstoque().getDeposito()).comQuantidade(q.getQuantidade())
                            .comItem(in.getItem()).comOperacaoDeEstoque(operacaoDeEstoque).comEmissao(nota.getEmissao()).comItemDeNota(in).construir();
                    // Adiciona no estoque
                    in.adicionaNoEstoque(e);
                }
            }
        }
    }

}
