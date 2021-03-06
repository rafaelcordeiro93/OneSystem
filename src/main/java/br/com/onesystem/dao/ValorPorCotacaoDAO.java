/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.domain.FaturaEmitida;
import br.com.onesystem.domain.FaturaRecebida;

/**
 *
 * @author Rafael
 */
public class ValorPorCotacaoDAO extends GenericDAO<ValorPorCotacao> {

    public ValorPorCotacaoDAO() {
        super(ValorPorCotacao.class);
        limpar();
    }

    public ValorPorCotacaoDAO porId(Long id) {
        if (id != null) {
            where += " and valorPorCotacao.id = :cId ";
            parametros.put("cId", id);
        }
        return this;
    }

    public ValorPorCotacaoDAO porFaturaEmitida(FaturaEmitida faturaEmitida) {
        if (faturaEmitida != null) {
            where += " and valorPorCotacao.faturaEmitida = :cFaturaEmitida ";
            parametros.put("cFaturaEmitida", faturaEmitida);
        }
        return this;
    }

    public ValorPorCotacaoDAO porFaturaRecebida(FaturaRecebida faturaRecebida) {
        if (faturaRecebida != null) {
            where += " and valorPorCotacao.faturaRecebida = :cFaturaRecebida ";
            parametros.put("cFaturaRecebida", faturaRecebida);
        }
        return this;
    }

    public ValorPorCotacaoDAO porConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        if (conhecimentoDeFrete != null) {
            where += " and valorPorCotacao.conhecimentoDeFrete = :cConhecimentoDeFrete ";
            parametros.put("cConhecimentoDeFrete", conhecimentoDeFrete);
        }
        return this;
    }

}
