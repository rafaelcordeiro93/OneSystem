/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaEmitidaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public NotaEmitidaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "select n from NotaEmitida n where n.id > 0 ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public NotaEmitidaDAO porId(Long id) {
        consulta += " and n.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public NotaEmitidaDAO porTipoOperacao(TipoOperacao tipoOperacao) {
        consulta += " and n.operacao.tipoOperacao = :pTipoOperacao";
        parametros.put("pTipoOperacao", tipoOperacao);
        return this;
    }

    public NotaEmitidaDAO porCondicional(Condicional condicional) {
        consulta += " and n.condicional = :pCondicional ";
        parametros.put("pCondicional", condicional);
        return this;
    }

    public List<NotaEmitida> listaDeResultados() {
        List<NotaEmitida> resultado = new ArmazemDeRegistros<NotaEmitida>(NotaEmitida.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public NotaEmitida resultado() throws DadoInvalidoException {
        try {
            NotaEmitida resultado = new ArmazemDeRegistros<NotaEmitida>(NotaEmitida.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
