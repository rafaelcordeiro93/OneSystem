/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.ContaDeEstoque;
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
public class ContaDeEstoqueDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ContaDeEstoqueDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ContaDeEstoqueDAO buscarContaDeEstoqueW() {
        consulta += "select c from ContaDeEstoque c where c.id > 0 ";
        return this;
    }

    public ContaDeEstoqueDAO PorNome(ContaDeEstoque contaDeEstoque) {
        consulta += "and c.nome = :cNome ";
        parametros.put("cNome", contaDeEstoque.getNome());
        return this;
    }

    public ContaDeEstoqueDAO porId(Long id) {
        consulta += " and c.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

    public List<ContaDeEstoque> listaDeResultados() {
        List<ContaDeEstoque> resultado = new ArmazemDeRegistros<ContaDeEstoque>(ContaDeEstoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public ContaDeEstoque resultado() throws DadoInvalidoException {
        try {
            ContaDeEstoque resultado = new ArmazemDeRegistros<ContaDeEstoque>(ContaDeEstoque.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
