/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.onesystem.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 *
 * @author Rafael
 */
public class NumberUtils {
    
     public static String format(BigDecimal valor) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(valor);
    }
    
}
