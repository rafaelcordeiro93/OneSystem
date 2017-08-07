/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.CobrancaFixa;

/**
 *
 * @author Rafael Cordeiro
 */
public class CobrancaFixaDAO extends GenericDAO<CobrancaFixa> {

    public CobrancaFixaDAO() {
        super(CobrancaFixa.class);
        limpar();
    }

    public CobrancaFixaDAO consultaDespesasProvionadas() {
        query = "select despesaProvisionada from DespesaProvisionada despesaProvisionada ";
        where = " where despesaProvisionada.id != 0 ";
        return this;
    }

    public CobrancaFixaDAO consultaReceitasProvisionadas() {
        query = "select receitaProvisionada from ReceitaProvisionada receitaProvisionada ";
        where = " where receitaProvisionada.id != 0 ";
        return this;
    }

}
