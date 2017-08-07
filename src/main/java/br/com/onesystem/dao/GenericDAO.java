/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoDeBusca;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.NoResultException;

/**
 *
 * @author Rafael Fernando Rauber
 * @param <T>
 */
public abstract class GenericDAO<T> {

    private T t;
    private Class clazz;
    protected String query;
    protected String where;
    protected String join;
    protected String order;
    protected String group;
    protected BundleUtil msg;
    protected Map<String, Object> parametros;

    public GenericDAO(Class clazz) {
        this.clazz = clazz;
        limpar();
    }

    public String getConsulta() {
        return query + join + where + order + group;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    protected void limpar() {
        String simpleNameMinusculo = clazz.getSimpleName().toLowerCase().substring(0, 1) + clazz.getSimpleName().substring(1);
        query = "select " + simpleNameMinusculo + " from " + clazz.getSimpleName() + " " + simpleNameMinusculo;
        join = " ";
        where = " where " + simpleNameMinusculo + ".id != 0 ";
        order = " ";
        group = " ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public List<T> listaDeResultados() {
        List<T> resultado = new ArmazemDeRegistros<T>((Class<T>) clazz).listaRegistrosDaConsulta(getConsulta(), parametros);
        limpar();
        return resultado;
    }

    public T resultado() throws DadoInvalidoException {
        try {
            T resultado = new ArmazemDeRegistros<T>((Class<T>) clazz)
                    .resultadoUnicoDaConsulta(getConsulta(), parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

    public GenericDAO filter(TipoDeBusca tipo, Coluna campo, Object filtro) {
        String ALIAS = query.split(" ")[4];
        Date date = filtro instanceof Date ? (Date) filtro : null;
        SortedSet<String> conjuntoString = filtro instanceof SortedSet && !((SortedSet) filtro).isEmpty() && ((SortedSet) filtro).first() instanceof String ? (TreeSet<String>) filtro : null;
        SortedSet<Long> conjuntoLong = filtro instanceof SortedSet && !((SortedSet) filtro).isEmpty() && ((SortedSet) filtro).first() instanceof Long ? (TreeSet<Long>) filtro : null;
        SortedSet<BigDecimal> conjuntoBigDecimal = filtro instanceof SortedSet && !((SortedSet) filtro).isEmpty() && ((SortedSet) filtro).first() instanceof BigDecimal ? (TreeSet<BigDecimal>) filtro : null;

        String igualOuIn = filtro instanceof SortedSet ? "in" : "=";
        String diferenteOuNotIn = filtro instanceof SortedSet ? "not in" : "!=";

        String parametro = ":p" + tipo + "_" + campo.getPropriedadeCompleta().substring(0, 1).toUpperCase() + campo.getPropriedadeCompleta().substring(1).replaceAll("\\.", "_");
        
        if (tipo == TipoDeBusca.IGUAL_A) {
            if (conjuntoString != null) {
                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") " + igualOuIn + " lower(" + parametro + ")";
                parametros.put(parametro.substring(1), conjuntoString);
            } else if (date != null) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " between " + parametro + "Inicial and " + parametro + "Final";
                parametros.put(parametro.substring(1) + "Inicial", date);
                parametros.put(parametro.substring(1) + "Final", Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()));
            } else {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " " + igualOuIn + " " + parametro + "";
                parametros.put(parametro.substring(1), conjuntoLong != null ? conjuntoLong : conjuntoBigDecimal != null ? conjuntoBigDecimal : null);
            }
        } else if (tipo == TipoDeBusca.DIFERENTE_DE) {
            if (conjuntoString != null) {
                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") " + diferenteOuNotIn + " lower(" + parametro + ")";
                parametros.put(parametro.substring(1), conjuntoString);
            } else if (date != null) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " not between " + parametro + "Inicial and " + parametro + "Final";
                parametros.put(parametro.substring(1) + "Inicial", date);
                parametros.put(parametro.substring(1) + "Final", Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()));
            } else {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " " + diferenteOuNotIn + " " + parametro;
                parametros.put(parametro.substring(1), conjuntoLong != null ? conjuntoLong : conjuntoBigDecimal != null ? conjuntoBigDecimal : null);
            }
            parametros.put(parametro.substring(1), conjuntoString != null ? conjuntoString : conjuntoLong != null ? conjuntoLong : conjuntoBigDecimal != null ? conjuntoBigDecimal : date != null ? date : null);
        } else if (conjuntoString != null && (tipo == TipoDeBusca.INICIANDO || tipo == TipoDeBusca.TERMINANDO || tipo == TipoDeBusca.CONTENDO)) {
            String s = " and ";

            int i = 0;
            for (final Iterator it = conjuntoString.iterator(); it.hasNext();) {
                String par = parametro + "_" + i;
                where += s + "lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + par + ")";
                s = " or ";
                if (tipo == TipoDeBusca.INICIANDO) {
                    parametros.put(par.substring(1), it.next().toString() + "%");
                } else if (tipo == TipoDeBusca.TERMINANDO) {
                    parametros.put(par.substring(1), "%" + it.next().toString());
                } else if (tipo == TipoDeBusca.CONTENDO) {
                    parametros.put(par.substring(1), "%" + it.next().toString() + "%");
                }
                i++;
            }
            where += ")";
        } else if ((tipo == TipoDeBusca.MAIOR_QUE || tipo == TipoDeBusca.MAIOR_OU_IGUAL_A || tipo == TipoDeBusca.MENOR_QUE || tipo == TipoDeBusca.MENOR_OU_IGUAL_A) && conjuntoString == null) {
            if (tipo == TipoDeBusca.MAIOR_QUE) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " > " + parametro;
            } else if (tipo == TipoDeBusca.MAIOR_OU_IGUAL_A) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " >= " + parametro;
            } else if (tipo == TipoDeBusca.MENOR_QUE) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " < " + parametro;
            } else if (tipo == TipoDeBusca.MENOR_OU_IGUAL_A) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " <= " + parametro;
            }
            parametros.put(parametro.substring(1), conjuntoLong != null ? conjuntoLong : conjuntoBigDecimal != null ? conjuntoBigDecimal : date != null ? date : null);
        }

        return this;
    }

}
