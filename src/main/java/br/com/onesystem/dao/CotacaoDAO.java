/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

/**
 *
 * @author Rafael
 */
public class CotacaoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public CotacaoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public CotacaoDAO buscarCotacoes() {
        consulta += "select c from Cotacao c where c.id > 0 ";
        return this;
    }

    public CotacaoDAO porId(Long id) {
        consulta += " and c.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

    public CotacaoDAO porMoeda(Moeda moeda) {
        consulta += " and c.moeda = :cMoeda ";
        parametros.put("cMoeda", moeda);
        return this;
    }

    public CotacaoDAO naEmissao(Date emissao) {
        consulta += "and c.emissao between :pEmissao and :pEmissaoFinal";
        parametros.put("pEmissao", getDataComHoraZerada(emissao));
        parametros.put("pEmissaoFinal", getDataComHoraFimDoDia(emissao));
        return this;
    }

    public CotacaoDAO naMaiorEmissao(Date emissao) {
        consulta += "and c.emissao in (select max(ct.emissao) from Cotacao ct "
                + "where ct.emissao between :pEmissao and :pEmissaoFinal "
                + "group by ct.moeda,ct.conta) ";
        parametros.put("pEmissao", getDataComHoraZerada(emissao));
        parametros.put("pEmissaoFinal", getDataComHoraFimDoDia(emissao));
        return this;
    }

    private Date getDataComHoraZerada(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    private Date getDataComHoraFimDoDia(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public List<Cotacao> listaDeResultados() {
        List<Cotacao> resultado = new ArmazemDeRegistros<Cotacao>(Cotacao.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Cotacao resultado() throws DadoInvalidoException {
        try {
            Cotacao resultado = new ArmazemDeRegistros<Cotacao>(Cotacao.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
