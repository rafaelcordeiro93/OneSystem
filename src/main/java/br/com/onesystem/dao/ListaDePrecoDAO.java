/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.ListaDePreco;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ListaDePrecoDAO extends GenericDAO<ListaDePreco> {

    public ListaDePrecoDAO() {
        super(ListaDePreco.class);
    }

    public ListaDePrecoDAO porId(Long id) {
        where += "and listaDePreco.id = :lId ";
        parametros.put("lId", id);
        return this;
    }

    public ListaDePrecoDAO porNome(ListaDePreco listaDePreco) {
        where += " and listaDePreco.nome = :lNome ";
        parametros.put("lNome", listaDePreco.getNome());
        return this;
    }

}
