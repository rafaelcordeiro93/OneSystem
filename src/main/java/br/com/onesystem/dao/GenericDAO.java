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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

// Utilizado para vários tipos de filtros, no entanto não necessário, porém fica comentado 
// para se houver necessidade em futuro próximo - Data Atual: 26/07/2017.
//
//    public GenericDAO filter(TipoDeBusca tipo, FieldModel campo, Object parametro) {
//        String ALIAS = query.split(" ")[4];
//        String ps = parametro instanceof String ? (String) parametro : null;
//        Long pl = parametro instanceof Long ? (Long) parametro : null;
//        BigDecimal pb = parametro instanceof BigDecimal ? (BigDecimal) parametro : null;
//        Date dt = parametro instanceof Date ? (Date) parametro : null;
//        List<String> ls = parametro instanceof List && !((List) parametro).isEmpty() && ((List) parametro).get(0) instanceof String ? (List) parametro : null;
//        List<Long> ll = parametro instanceof List && !((List) parametro).isEmpty() && ((List) parametro).get(0) instanceof Long ? (List) parametro : null;
//        List<BigDecimal> lb = parametro instanceof List && !((List) parametro).isEmpty() && ((List) parametro).get(0) instanceof BigDecimal ? (List) parametro : null;
//
//        String igualOuIn = parametro instanceof List ? "in" : "=";
//        String p = ":p" + campo.getPropriedadeCompleta().substring(0, 1).toUpperCase() + campo.getPropriedadeCompleta().substring(1).replaceAll("\\.", "_");
//
//        if (tipo == TipoDeBusca.IGUAL_A) {
//            if (ps != null || ls != null) {
//                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") " + igualOuIn + " lower(" + p + ")";
//            } else {
//                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " " + igualOuIn + " " + p + "";
//            }
//            parametros.put(p.substring(1), ps != null ? ps : pl != null ? pl : pb != null ? pb : ls != null ? ls : ll != null ? ll : lb != null ? lb : dt != null ? dt : null);
//        } else if (tipo == TipoDeBusca.DIFERENTE_DE) {
//            if (ps != null || ls != null) {
//                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") != lower(" + p + ")";
//            } else {
//                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " != " + p + "";
//            }
//            parametros.put(p.substring(1), ps != null ? ps : pl != null ? pl : pb != null ? pb : ls != null ? ls : ll != null ? ll : lb != null ? lb : dt != null ? dt : null);
//        } else if (tipo == TipoDeBusca.INICIANDO) {
//            if (ls == null) {
//                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + p + ")";
//                parametros.put(p.substring(1), ps + "%");
//            } else {
//                String s = " and ";
//                for (int i = 0; i < ls.size(); i++) {
//                    p = p + "_" + i;
//                    where += s + "lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + p + ")";
//                    parametros.put(p.substring(1), ls.get(i) + "%");
//                    s = " or ";
//                }
//                where += ")";
//            }
//        } else if (tipo == TipoDeBusca.TERMINANDO) {
//            if (ls == null) {
//                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + p + ")";
//                parametros.put(p.substring(1), "%" + ps);
//            } else {
//                String s = " and ";
//                for (int i = 0; i < ls.size(); i++) {
//                    p = p + "_" + i;
//                    where += s + "lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + p + ")";
//                    parametros.put(p.substring(1), "%" + ls.get(i));
//                    s = " or ";
//                }
//                where += ")";
//            }
//        } else if (tipo == TipoDeBusca.CONTENDO) {
//            if (ls == null) {
//                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + p + ")";
//                parametros.put(p.substring(1), "%" + ps + "%");
//            } else {
//                String s = " and ";
//                for (int i = 0; i < ls.size(); i++) {
//                    p = p + "_" + i;
//                    where += s + "lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + p + ")";
//                    parametros.put(p.substring(1), "%" + ls.get(i) + "%");
//                    s = " or ";
//                }
//                where += ")";
//            }
//        } else if (parametro instanceof Long || parametro instanceof BigDecimal || parametro instanceof Date) {
//            if (tipo == TipoDeBusca.MAIOR_QUE) {
//                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " > " + p;
//            } else if (tipo == TipoDeBusca.MAIOR_OU_IGUAL_A) {
//                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " >= " + p;
//            } else if (tipo == TipoDeBusca.MENOR_QUE) {
//                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " < " + p;
//            } else if (tipo == TipoDeBusca.MENOR_OU_IGUAL_A) {
//                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " <= " + p;
//            }
//            parametros.put(p.substring(1), pl != null ? pl : pb != null ? pb : dt != null ? dt : null);
//        }
//
//        return this;
//    }
    public GenericDAO filter(TipoDeBusca tipo, Coluna campo, Object filtro) {
        String ALIAS = query.split(" ")[4];
        Date date = filtro instanceof Date ? (Date) filtro : null;
        List<String> listaString = filtro instanceof List && !((List) filtro).isEmpty() && ((List) filtro).get(0) instanceof String ? (List) filtro : null;
        List<Long> listaLong = filtro instanceof List && !((List) filtro).isEmpty() && ((List) filtro).get(0) instanceof Long ? (List) filtro : null;
        List<BigDecimal> listaBigDecimal = filtro instanceof List && !((List) filtro).isEmpty() && ((List) filtro).get(0) instanceof BigDecimal ? (List) filtro : null;

        String igualOuIn = filtro instanceof List ? "in" : "=";
        String diferenteOuNotIn = filtro instanceof List ? "not in" : "!=";

        String parametro = ":p" + campo.getPropriedadeCompleta().substring(0, 1).toUpperCase() + campo.getPropriedadeCompleta().substring(1).replaceAll("\\.", "_");

        if (tipo == TipoDeBusca.IGUAL_A) {
            if (listaString != null) {
                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") " + igualOuIn + " lower(" + parametro + ")";
                parametros.put(parametro.substring(1), listaString);
            } else if (date != null) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " between " + parametro + "Inicial and " + parametro + "Final";
                parametros.put(parametro.substring(1) + "Inicial", date);
                parametros.put(parametro.substring(1) + "Final", Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()));
            } else {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " " + igualOuIn + " " + parametro + "";
                parametros.put(parametro.substring(1), listaLong != null ? listaLong : listaBigDecimal != null ? listaBigDecimal : null);
            }
        } else if (tipo == TipoDeBusca.DIFERENTE_DE) {
            if (listaString != null) {
                where += " and lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") " + diferenteOuNotIn + " lower(" + parametro + ")";
                parametros.put(parametro.substring(1), listaString);
            } else if (date != null) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " not between " + parametro + "Inicial and " + parametro + "Final";
                parametros.put(parametro.substring(1) + "Inicial", date);
                parametros.put(parametro.substring(1) + "Final", Date.from(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()));
            } else {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " " + diferenteOuNotIn + " " + parametro;
                parametros.put(parametro.substring(1), listaLong != null ? listaLong : listaBigDecimal != null ? listaBigDecimal : null);
            }
            parametros.put(parametro.substring(1), listaString != null ? listaString : listaLong != null ? listaLong : listaBigDecimal != null ? listaBigDecimal : date != null ? date : null);
        } else if (listaString != null) {
            String s = " and ";
            for (int i = 0; i < listaString.size(); i++) {
                parametro = parametro + "_" + i;
                where += s + "lower(" + ALIAS + "." + campo.getPropriedadeCompleta() + ") like lower(" + parametro + ")";
                s = " or ";
                if (tipo == TipoDeBusca.INICIANDO) {
                    parametros.put(parametro.substring(1), listaString.get(i) + "%");
                } else if (tipo == TipoDeBusca.TERMINANDO) {
                    parametros.put(parametro.substring(1), "%" + listaString.get(i));
                } else if (tipo == TipoDeBusca.CONTENDO) {
                    parametros.put(parametro.substring(1), "%" + listaString.get(i) + "%");
                }
            }
            where += ")";
        } else if ((tipo == TipoDeBusca.MAIOR_QUE || tipo == TipoDeBusca.MAIOR_OU_IGUAL_A || tipo == TipoDeBusca.MENOR_QUE || tipo == TipoDeBusca.MENOR_OU_IGUAL_A) && listaString == null) {
            if (tipo == TipoDeBusca.MAIOR_QUE) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " > " + parametro;
            } else if (tipo == TipoDeBusca.MAIOR_OU_IGUAL_A) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " >= " + parametro;
            } else if (tipo == TipoDeBusca.MENOR_QUE) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " < " + parametro;
            } else if (tipo == TipoDeBusca.MENOR_OU_IGUAL_A) {
                where += " and " + ALIAS + "." + campo.getPropriedadeCompleta() + " <= " + parametro;
            }
            parametros.put(parametro.substring(1), listaLong != null ? listaLong : listaBigDecimal != null ? listaBigDecimal : date != null ? date : null);
        }

        return this;
    }

}
