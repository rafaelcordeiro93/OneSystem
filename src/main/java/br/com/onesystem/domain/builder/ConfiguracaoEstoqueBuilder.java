/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ConfiguracaoEstoqueBuilder {

    private Long id;
    private ContaDeEstoque contaDeEstoque;
    private ListaDePreco listaDePreco;
    private Deposito depositoPadrao;

    public ConfiguracaoEstoqueBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ConfiguracaoEstoqueBuilder comContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        this.contaDeEstoque = contaDeEstoque;
        return this;
    }

    public ConfiguracaoEstoqueBuilder comDepositoPadrao(Deposito depositoPadrao) {
        this.depositoPadrao = depositoPadrao;
        return this;
    }

    public ConfiguracaoEstoqueBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public ConfiguracaoEstoque construir() throws DadoInvalidoException {
        return new ConfiguracaoEstoque(id, contaDeEstoque, listaDePreco, depositoPadrao);
    }

}
