/*
 * To change this license nome, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_PARAMETRODEFILTRODERELATORIO",
        sequenceName = "SEQ_PARAMETRODEFILTRODERELATORIO")
public class ParametroDeFiltroDeRelatorio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAMETRODEFILTRODERELATORIO")
    private Long id;
    private Long parametroLong;
    private String parametroString;
    private BigDecimal parametroBigDecimal;
    private String parametroEnum;
    @ManyToOne
    private FiltroDeRelatorio filtroDeRelatorio;

    public ParametroDeFiltroDeRelatorio() {
    }

    public ParametroDeFiltroDeRelatorio(Long id, Long parametroLong, FiltroDeRelatorio filtroDeRelatorio) {
        this.id = id;
        this.parametroLong = parametroLong;
        this.filtroDeRelatorio = filtroDeRelatorio;
    }

    public ParametroDeFiltroDeRelatorio(Long id, String parametroString, FiltroDeRelatorio filtroDeRelatorio) {
        this.id = id;
        this.parametroString = parametroString;
        this.filtroDeRelatorio = filtroDeRelatorio;
    }

    public ParametroDeFiltroDeRelatorio(Long id, BigDecimal parametroBigDecimal, FiltroDeRelatorio filtroDeRelatorio) {
        this.id = id;
        this.parametroBigDecimal = parametroBigDecimal;
        this.filtroDeRelatorio = filtroDeRelatorio;
    }
    
     public ParametroDeFiltroDeRelatorio(Long id, FiltroDeRelatorio filtroDeRelatorio, String parametroEnum) {
        this.id = id;
        this.parametroEnum = parametroEnum;
        this.filtroDeRelatorio = filtroDeRelatorio;
    }

    public Long getId() {
        return id;
    }

    public Long getParametroLong() {
        return parametroLong;
    }

    public String getParametroString() {
        return parametroString;
    }

    public BigDecimal getParametroBigDecimal() {
        return parametroBigDecimal;
    }

    public FiltroDeRelatorio getFiltroDeRelatorio() {
        return filtroDeRelatorio;
    }

    public String getParametroEnum() {
        return parametroEnum;
    }
    
}
