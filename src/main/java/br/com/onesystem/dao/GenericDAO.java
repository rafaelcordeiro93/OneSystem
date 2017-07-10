/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rafael Fernando Rauber
 * @param <T>
 */
public abstract class GenericDAO<T> {

    protected String query;
    protected String where;
    protected String join;
    protected String order;
    protected String group;
    protected BundleUtil msg;
    protected Map<String, Object> parametros;

    public GenericDAO() {
        limpar();
    }

    public String getConsulta() {
        return query + join + where + order + group;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    protected abstract void limpar();

    public abstract List<T> listaDeResultados();

    public abstract T resultado() throws DadoInvalidoException;

}
