/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class CotacaoDAO extends GenericDAO<Cotacao> {

    public CotacaoDAO() {
        super(Cotacao.class);
        limpar();
    }

    public CotacaoDAO porId(Long id) {
        where += " and cotacao.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

    public CotacaoDAO porConta(Conta conta) {
        where += " and cotacao.conta = :cConta ";
        parametros.put("cConta", conta);
        return this;
    }

    public CotacaoDAO porMoeda(Moeda moeda) {
        where += " and cotacao.conta.moeda = :cMoeda ";
        parametros.put("cMoeda", moeda);
        return this;
    }

    public CotacaoDAO naEmissao(Date emissao) {
        where += " and cotacao.emissao between :pEmissao and :pEmissaoFinal";
        parametros.put("pEmissao", getDataComHoraZerada(emissao));
        parametros.put("pEmissaoFinal", getDataComHoraFimDoDia(emissao));
        return this;
    }

    public CotacaoDAO porCotacaoBancaria() {
        where += " and cotacao.conta.banco is not null ";
        return this;
    }

    public CotacaoDAO porCotacaoEmpresa() {
        where += " and cotacao.conta.banco is null ";
        return this;
    }

    public CotacaoDAO naMaiorEmissao(Date emissao) {
        where += " and cotacao.emissao in (select max(ct.emissao) from Cotacao ct "
                + "where ct.emissao between :pEmissao and :pEmissaoFinal "
                + "group by ct.conta) ";
        parametros.put("pEmissao", getDataComHoraZerada(emissao));
        parametros.put("pEmissaoFinal", getDataComHoraFimDoDia(emissao));
        return this;
    }

    public CotacaoDAO naUltimaEmissao(Date emissao) {
        where += " and cotacao.emissao in (select max(ct.emissao) from Cotacao ct where ct.emissao <= :pUltimaEmissao group by ct.conta) ";
        parametros.put("pUltimaEmissao", emissao);
        return this;
    }

    private Date getDataComHoraZerada(Date data) {
        Calendar cotacao = Calendar.getInstance();
        cotacao.setTime(data);
        cotacao.set(Calendar.HOUR_OF_DAY, 0);
        cotacao.set(Calendar.MINUTE, 0);
        cotacao.set(Calendar.SECOND, 0);
        return cotacao.getTime();
    }

    private Date getDataComHoraFimDoDia(Date data) {
        Calendar cotacao = Calendar.getInstance();
        cotacao.setTime(data);
        cotacao.set(Calendar.HOUR_OF_DAY, 23);
        cotacao.set(Calendar.MINUTE, 59);
        cotacao.set(Calendar.SECOND, 59);
        return cotacao.getTime();
    }

}
