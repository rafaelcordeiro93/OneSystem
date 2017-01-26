/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class MargemDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public MargemDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public MargemDAO buscarMargemW() {
        consulta += "select m from Margem m where m.id > 0 ";
        return this;
    }

    public MargemDAO porId(Long id) {
        consulta += "and m.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public List<Margem> listaDeResultados() {
        List<Margem> resultado = new ArmazemDeRegistros<Margem>(Margem.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Margem resultado() throws DadoInvalidoException {
        try {
            Margem resultado = new ArmazemDeRegistros<Margem>(Margem.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);        
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("margem_nao_encontrada"));
        }
    }

}
