/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.reportTemplate;

import br.com.onesystem.domain.Moeda;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class SomaSaldoDeTituloPorMoedaReportTemplate {

    private Moeda moeda;

    private BigDecimal valor;

    public SomaSaldoDeTituloPorMoedaReportTemplate(Moeda moeda, BigDecimal valor) {
        this.moeda = moeda;
        this.valor = valor;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public BigDecimal getValor() {
        return valor;
    }
    
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof SomaSaldoDeTituloPorMoedaReportTemplate)) {
            return false;
        }
        SomaSaldoDeTituloPorMoedaReportTemplate outro = (SomaSaldoDeTituloPorMoedaReportTemplate) objeto;
        if (this.moeda == null) {
            return false;
        }
        return this.moeda.equals(outro.moeda);
    }
    
}
