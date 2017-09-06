/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ContaDAO extends GenericDAO<Conta> {

    public ContaDAO() {
        super(Conta.class);
        limpar();
    }

    public ContaDAO ePorMoeda(Moeda moeda) {
        where += "and conta.moeda = :pMoeda ";
        parametros.put("pMoeda", moeda);
        return this;
    }

    public ContaDAO ePorMoedas(List<Moeda> moedas) {
        where += " and conta.moeda in :pMoedas ";
        parametros.put("pMoedas", moedas);
        return this;
    }

    public ContaDAO comBanco() {
        where += "and conta.banco is not null ";
        return this;
    }

    public ContaDAO semBanco() {
        where += "and conta.banco is null ";
        return this;
    }

    public ContaDAO porId(Long id) {
        where += " and conta.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

}
