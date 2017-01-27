/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Receita;
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
public class ReceitaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ReceitaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ReceitaDAO buscarReceitaW() {
        consulta += "select r from Receita r where r.id > 0 ";
        return this;
    }

    public ReceitaDAO ePorMoeda(Receita receita) {
        consulta += "and r.nome = :rNome ";
        parametros.put("rNome", receita.getNome());
        return this;
    }

    public ReceitaDAO porId(Long id) {
        consulta += " and r.id = :rId ";
        parametros.put("rId", id);
        return this;
    }

    public List<Receita> listaDeResultados() {
        List<Receita> resultado = new ArmazemDeRegistros<Receita>(Receita.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Receita resultado() throws DadoInvalidoException {
        try {
            Receita resultado = new ArmazemDeRegistros<Receita>(Receita.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
