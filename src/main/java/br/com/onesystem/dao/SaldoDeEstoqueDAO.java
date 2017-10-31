/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.SaldoDeEstoque;
import br.com.onesystem.reportTemplate.SaldoEmContaTemplate;
import br.com.onesystem.reportTemplate.SaldoEmDepositoTemplate;
import br.com.onesystem.reportTemplate.SaldoEmLoteTemplate;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Rafael
 */
public class SaldoDeEstoqueDAO extends GenericDAO<SaldoDeEstoque> {

    public SaldoDeEstoqueDAO() {
        super(SaldoDeEstoque.class);
    }

    public SaldoDeEstoqueDAO buscaSaldoDeCadaDeposito() {
        query = "select new br.com.onesystem.reportTemplate.SaldoEmDepositoTemplate"
                + "(saldoDeEstoque.deposito, saldoDeEstoque.item, sum(saldoDeEstoque.saldo)) "
                + "from SaldoDeEstoque saldoDeEstoque ";
        return this;
    }

    public SaldoDeEstoqueDAO buscaSaldoDeCadaConta() {
        query = "select new br.com.onesystem.reportTemplate.SaldoEmContaTemplate"
                + "(saldoDeEstoque.contaDeEstoque, saldoDeEstoque.item, sum(saldoDeEstoque.saldo)) "
                + "from SaldoDeEstoque saldoDeEstoque ";
        return this;
    }

    public SaldoDeEstoqueDAO buscaSaldoDeCadaLote() {
        query = "select new br.com.onesystem.reportTemplate.SaldoEmLoteTemplate"
                + "(saldoDeEstoque.loteItem, saldoDeEstoque.item, sum(saldoDeEstoque.saldo)) "
                + "from SaldoDeEstoque saldoDeEstoque ";
        return this;
    }

    public SaldoDeEstoqueDAO porContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        where += " and saldoDeEstoque.contaDeEstoque = :pContaDeEstoque ";
        parametros.put("pContaDeEstoque", contaDeEstoque);
        return this;
    }

    public SaldoDeEstoqueDAO porItem(Item item) {
        where += " and saldoDeEstoque.item = :pItem ";
        parametros.put("pItem", item);
        return this;
    }

    public SaldoDeEstoqueDAO groupByDepositoItem() {
        group = " group by saldoDeEstoque.deposito,saldoDeEstoque.item ";
        return this;
    }

    public SaldoDeEstoqueDAO groupByContaDeEstoqueItem() {
        group = " group by saldoDeEstoque.contaDeEstoque,saldoDeEstoque.item ";
        return this;
    }

    public SaldoDeEstoqueDAO groupByLoteDeEstoqueItem() {
        group = " group by saldoDeEstoque.loteItem,saldoDeEstoque.item ";
        return this;
    }

    public List<SaldoEmDepositoTemplate> listaDeDepositosSoma(EntityManager manager) {
        List listaRegistrosDaConsulta = armazem.daClasse((Class<SaldoEmDepositoTemplate>) SaldoEmDepositoTemplate.class, manager).listaRegistrosDaConsulta(getConsulta(), parametros);
        limpar();
        return listaRegistrosDaConsulta;
    }

    public List<SaldoEmContaTemplate> listaDeContaSoma(EntityManager manager) {
        List listaRegistrosDaConsulta = armazem.daClasse((Class<SaldoEmContaTemplate>) SaldoEmContaTemplate.class, manager).listaRegistrosDaConsulta(getConsulta(), parametros);
        limpar();
        return listaRegistrosDaConsulta;
    }

    public List<SaldoEmLoteTemplate> listaDeLoteSoma(EntityManager manager) {
        List listaRegistrosDaConsulta = armazem.daClasse((Class<SaldoEmLoteTemplate>) SaldoEmLoteTemplate.class, manager).listaRegistrosDaConsulta(getConsulta(), parametros);
        limpar();
        return listaRegistrosDaConsulta;
    }

}
