/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.NotaEmitida;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaDAO extends GenericDAO<NotaEmitida> {

    public NotaDAO() {
        super(NotaEmitida.class);
        limpar();
    }

}
