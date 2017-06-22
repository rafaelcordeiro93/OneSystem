/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.exception.DadoInvalidoException;
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
public class ListaDePrecoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ListaDePrecoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ListaDePrecoDAO buscarListaDePrecoW() {
        consulta += "select l from ListaDePreco l where l.id > 0 ";
        return this;
    }

    public ListaDePrecoDAO porId(Long id) {
        consulta += "and l.id = :lId ";
        parametros.put("lId", id);
        return this;
    }

    public ListaDePrecoDAO porNome(ListaDePreco listaDePreco) {
        consulta += " and l.nome = :lNome ";
        parametros.put("lNome", listaDePreco.getNome());
        return this;
    }

    public List<ListaDePreco> listaDeResultados() {
        List<ListaDePreco> resultado = new ArmazemDeRegistros<ListaDePreco>(ListaDePreco.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public ListaDePreco resultado() throws DadoInvalidoException {
        try {
            ListaDePreco resultado = new ArmazemDeRegistros<ListaDePreco>(ListaDePreco.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
