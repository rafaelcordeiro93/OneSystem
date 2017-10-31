/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service;

import br.com.onesystem.dao.SaldoDeEstoqueDAO;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.SaldoDeEstoque;
import br.com.onesystem.reportTemplate.SaldoEmContaTemplate;
import br.com.onesystem.reportTemplate.SaldoEmDepositoTemplate;
import br.com.onesystem.reportTemplate.SaldoEmLoteTemplate;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Rafael
 */
@Stateless
public class SaldoDeEstoqueService implements Serializable {

    @Inject
    private EntityManager manager;

    @Inject
    private SaldoDeEstoqueDAO saldoDAO;

    public List<SaldoDeEstoque> buscaSaldoDeEstoquePorItem(Item item) {
        return saldoDAO.porItem(item).listaDeResultados(manager);
    }

    public List<SaldoEmDepositoTemplate> buscaListaDeSaldoDeEstoqueEmTodosDepositos(ContaDeEstoque contaDeEstoque, Item item) {
        return saldoDAO.buscaSaldoDeCadaDeposito().porItem(item).porContaDeEstoque(contaDeEstoque).groupByDepositoItem().listaDeDepositosSoma(manager);
    }

    public List<SaldoEmContaTemplate> buscaListaDeSaldoDeEstoqueEmTodasContas(Item item) {
        return saldoDAO.buscaSaldoDeCadaConta().porItem(item).groupByContaDeEstoqueItem().listaDeContaSoma(manager);
    }

    public List<SaldoEmLoteTemplate> buscaListaDeSaldoDeEstoqueEmTodosLotes(ContaDeEstoque contaDeEstoque, Item item) {
        return saldoDAO.buscaSaldoDeCadaLote().porItem(item).porContaDeEstoque(contaDeEstoque).groupByLoteDeEstoqueItem().listaDeLoteSoma(manager);
    }

}
