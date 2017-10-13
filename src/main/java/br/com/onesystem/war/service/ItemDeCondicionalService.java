/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service;

import br.com.onesystem.dao.OperacaoDeEstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.builder.EstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Rafael
 */
public class ItemDeCondicionalService implements Serializable {

    @Inject
    private ConfiguracaoVenda configuracaoVenda;

    @Inject
    private ConfiguracaoEstoque configuracaoEstoque;
    
    @Inject
    private OperacaoDeEstoqueDAO dao;

    public void geraEstoque(ItemDeCondicional itemDeCondicional) throws DadoInvalidoException {
        List<OperacaoDeEstoque> listaOperacaoEstoque = dao.porOperacao(configuracaoVenda.getOperacaoDeCondicional()).listaDeResultados();
        for (OperacaoDeEstoque operacaoDeEstoque : listaOperacaoEstoque) {
            Estoque e = new EstoqueBuilder().comDeposito(configuracaoEstoque.getDepositoPadrao()).comQuantidade(itemDeCondicional.getQuantidade())
                    .comItem(itemDeCondicional.getItem()).comOperacaoDeEstoque(operacaoDeEstoque).comEmissao(itemDeCondicional.getCondicional().getEmissao()).comItemDeCondicional(itemDeCondicional).construir();
            // Adiciona no estoque
            itemDeCondicional.adicionaEstoque(e);
        }
    }
}
