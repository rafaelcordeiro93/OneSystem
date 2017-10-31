/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.services;

import br.com.onesystem.domain.AjusteDeEstoque;
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
import java.util.ArrayList;
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
public class GeradorDeEstoque implements Serializable {

    @Inject
    private OperacaoDeEstoqueService operacaoDeEstoqueService;

    /**
     * Gera o estoque dos itens da nota. Realiza as condições:
     *
     * Se nao for condicional ou for condicional e a operacao de estoque da
     * condicional for diferente a operacao de estoque da operação da nota
     * emitida
     *
     * Caso de uso 1: Se não for condicional, precisa gerar estoque
     *
     * Caso de uso 2: Se for condicional e não foi dado saida de estoque da
     * mercadoria na emissão da condicional, deve realizar a saída de estoque na
     * emissão da nota.
     *
     * Caso de uso 3: Se for Nota Recebida deve gerar o estoque
     *
     * @param nota Recebe a nota para geração de estoque
     *
     * @throws br.com.onesystem.exception.DadoInvalidoException pode gerar erro
     * de validação de campos
     */
    public void geraEstoqueDe(Nota nota) throws DadoInvalidoException {
        if (nota instanceof NotaEmitida) {
            NotaEmitida notaEmitida = (NotaEmitida) nota;
            // se nao for condicional ou for condicional e a operacao de estoque da condicional for diferente a operacao de estoque da operação da nota emitida
            // Caso de uso 1: Se não for condicional, precisa gerar estoque
            // Caso de uso 2: Se for condicional e não foi dado saida de estoque da mercadoria na emissão da condicional, deve realizar a saída de estoque na emissão da nota.
            if (notaEmitida.getCondicional() == null || (notaEmitida.getCondicional() != null && !(notaEmitida.getCondicional().getOperacao().getOperacaoDeEstoque().equals(notaEmitida.getOperacao().getOperacaoDeEstoque())))) {
                geraEstoqueDeNota(nota);
            }
        } else {
            //Caso de uso 3: Se for Nota Recebida deve gerar o estoque
            geraEstoqueDeNota(nota);
        }

    }

    private void geraEstoqueDeNota(Nota nota) throws DadoInvalidoException {
        List<OperacaoDeEstoque> listaDeOperacoes = operacaoDeEstoqueService.buscarOperacoesDeEstoquePor(nota.getOperacao());
        for (ItemDeNota in : nota.getItens()) {
            for (QuantidadeDeItemPorDeposito q : in.getListaDeQuantidade()) {

                for (OperacaoDeEstoque operacaoDeEstoque : listaDeOperacoes) {
                    Estoque e = new EstoqueBuilder().comDeposito(q.getSaldoDeEstoque().getDeposito()).comQuantidade(q.getQuantidade()).comContaDeEstoque(operacaoDeEstoque.getContaDeEstoque())
                            .comItem(in.getItem()).comOperacaoDeEstoque(operacaoDeEstoque).comEmissao(nota.getEmissao()).comItemDeNota(in).comLoteItem(in.getLoteItem()).construir();
                    // Adiciona no estoque
                    in.adicionaNoEstoque(e);
                }
            }
        }
    }

     public void geraEstoque(AjusteDeEstoque ajuste) throws DadoInvalidoException {
        List<OperacaoDeEstoque> listaOpEstoque = operacaoDeEstoqueService.buscarOperacoesDeEstoquePor(ajuste.getOperacao());
        List<Estoque> adicionar = new ArrayList<>();
        if (ajuste.getEstoque() == null) {
            ajuste.inicializaEstoque();
        }
        for (OperacaoDeEstoque op : listaOpEstoque) {
            boolean encontrou = false;
            for (Estoque e : ajuste.getEstoque()) {
                if (ajuste.getId() != null) {
                    e.atualizaQuantidade(ajuste.getQuantidade());
                    encontrou = true;
                    break;
                }
            }
            if (!encontrou) {
                adicionar.add(new EstoqueBuilder().comDeposito(ajuste.getDeposito()).comItem(ajuste.getItem()).comEmissao(ajuste.getEmissao()).comQuantidade(ajuste.getQuantidade())
                        .comContaDeEstoque(op.getContaDeEstoque()).comOperacaoDeEstoque(op).comLoteItem(ajuste.getLoteItem()).construir());
            }
        }
        for (Estoque a : adicionar) {
            ajuste.adiciona(a);
        }
    }
    
}
