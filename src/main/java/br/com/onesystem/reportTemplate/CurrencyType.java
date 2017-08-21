/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;

/**
 *
 * @author Rafael
 */
public class CurrencyType extends BigDecimalType {

    private static final long serialVersionUID = 1L;
    private final String sigla;

    public CurrencyType(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String getPattern() {
        return sigla + " #,###.00";
    }
}
