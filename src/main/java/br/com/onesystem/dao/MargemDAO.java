/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Margem;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class MargemDAO extends GenericDAO<Margem> {

    public MargemDAO() {
        super(Margem.class);
    }

    public MargemDAO porId(Long id) {
        where += "and margem.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

}
