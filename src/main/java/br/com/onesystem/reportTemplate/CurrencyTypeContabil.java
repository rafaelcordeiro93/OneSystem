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
public class CurrencyTypeContabil extends BigDecimalType {

    private static final long serialVersionUID = 1L;

    @Override
    public String getPattern() {
        return "#,###.00";
    }
}
