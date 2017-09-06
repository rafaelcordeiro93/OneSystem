/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Cfop;

/**
 *
 * @author Rafael
 */
public class CfopDAO extends GenericDAO<Cfop> {

    public CfopDAO() {
        super(Cfop.class);
        limpar();
    }

    public CfopDAO porId(Long id) {
        where += " and cfop.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

}
