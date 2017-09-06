/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editotipoReceita.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.TipoReceita;

/**
 *
 * @author Rafael
 */
public class ReceitaDAO extends GenericDAO<TipoReceita> {

    public ReceitaDAO() {
        super(TipoReceita.class);
    }

    public ReceitaDAO ePorMoeda(TipoReceita receita) {
        where += "and tipoReceita.nome = :rNome ";
        parametros.put("rNome", receita.getNome());
        return this;
    }

    public ReceitaDAO porId(Long id) {
        where += " and tipoReceita.id = :rId ";
        parametros.put("rId", id);
        return this;
    }

}
