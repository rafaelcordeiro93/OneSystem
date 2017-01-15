/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.BundleUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public CotacaoDAO naData(Date data) {
        consulta += "and c.data between :pData and :pDataFinal";
        parametros.put("pData", getDataComHoraZerada(data));
        parametros.put("pDataFinal", getDataComHoraFimDoDia(data));
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

}
