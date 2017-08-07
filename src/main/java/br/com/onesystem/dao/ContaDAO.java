/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

/**
 *
 * @author Rafael
 */
public class ContaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ContaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ContaDAO buscarContaW() {
        consulta += "select c from Conta c where c.id > 0 ";
        return this;
    }

    public ContaDAO ePorMoeda(Moeda moeda) {
        consulta += "and c.moeda = :pMoeda ";
        parametros.put("pMoeda", moeda);
        return this;
    }

    public ContaDAO ePorMoedas(List<Moeda> moedas) {
        consulta += " and c.moeda in :pMoedas ";
        parametros.put("pMoedas", moedas);
        return this;
    }

    public ContaDAO comBanco() {
        consulta += "and c.banco is not null ";
        return this;
    }

    public ContaDAO semBanco() {
        consulta += "and c.banco is null ";
        return this;
    }

    public ContaDAO porId(Long id) {
        consulta += " and c.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

    public String getConsulta() {
        return consulta;
    }

    public List<Conta> listaDeResultados() {
        List<Conta> resultado = new ArmazemDeRegistros<Conta>(Conta.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Conta resultado() throws DadoInvalidoException {
        try {
            Conta resultado = new ArmazemDeRegistros<Conta>(Conta.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
