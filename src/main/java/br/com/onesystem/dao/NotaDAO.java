/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Nota;

/**
 *
 * @author Rafael Cordeiro
 */
public class NotaDAO extends GenericDAO<Nota> {

    public NotaDAO() {
        super(Nota.class);
        limpar();
    }

    public NotaDAO consultaNotaEmitida() {
        query = "select notaEmitida from NotaEmitida notaEmitida ";
        where = " where notaEmitida.id != 0 ";
        return this;
    }

    public NotaDAO consultaNotaRecebida() {
        query = "select notaRecebida from NotaRecebida notaRecebida ";
        where = " where notaRecebida.id != 0 ";
        return this;
    }

}
