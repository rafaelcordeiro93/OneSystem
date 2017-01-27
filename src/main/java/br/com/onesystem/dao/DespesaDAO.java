/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Despesa;
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
public class DespesaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public DespesaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public DespesaDAO buscarDespesaW() {
        consulta += "select d from Despesa d where d.id > 0 ";
        return this;
    }

    public DespesaDAO ePorMoeda(Despesa despesa) {
        consulta += "and d.nome = :dNome ";
        parametros.put("dNome", despesa.getNome());
        return this;
    }

    public DespesaDAO porId(Long id) {
        consulta += " and d.id = :dId ";
        parametros.put("dId", id);
        return this;
    }

    public List<Despesa> listaDeResultados() {
        List<Despesa> resultado = new ArmazemDeRegistros<Despesa>(Despesa.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Despesa resultado() throws DadoInvalidoException {
        try {
            Despesa resultado = new ArmazemDeRegistros<Despesa>(Despesa.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
