/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.domain.Moeda;
import com.ibm.icu.text.NumberFormat;
import java.math.BigDecimal;

/**
 *
 * @author rauber
 */
public class MoedaFormatter {

    public static String format(Moeda moeda, Double valor) {
        Double v = valor == null ? 0 : valor == 0 ? 0 : valor;
        return NumberFormat.getCurrencyInstance(moeda.getBandeira().getLocal()).format(v);
    }

    public static String format(Moeda moeda, BigDecimal valor) {
        BigDecimal v = valor == null ? BigDecimal.ZERO : valor.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : valor;
        return NumberFormat.getCurrencyInstance(moeda.getBandeira().getLocal()).format(v);
    }

    public static String format(BigDecimal valor) {
        BigDecimal v = valor == null ? BigDecimal.ZERO : valor.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : valor;
        return NumberFormat.getCurrencyInstance().format(v);
    }

    public static String format(Double valor) {
        Double v = valor == null ? 0 : valor == 0 ? 0 : valor;
        return NumberFormat.getCurrencyInstance().format(v);
    }

}
