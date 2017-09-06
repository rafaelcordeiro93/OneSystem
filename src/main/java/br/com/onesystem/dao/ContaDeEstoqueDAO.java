/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.ContaDeEstoque;

/**
 *
 * @author Rafael
 */
public class ContaDeEstoqueDAO extends GenericDAO<ContaDeEstoque> {

    public ContaDeEstoqueDAO() {
        super(ContaDeEstoque.class);
        limpar();
    }

    public ContaDeEstoqueDAO PorNome(ContaDeEstoque contaDeEstoque) {
        where += "and contaDeEstoque.nome = :cNome ";
        parametros.put("cNome", contaDeEstoque.getNome());
        return this;
    }

    public ContaDeEstoqueDAO porId(Long id) {
        where += " and contaDeEstoque.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

}
