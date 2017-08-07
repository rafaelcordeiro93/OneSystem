/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rafael-Pc
 */
public enum NaturezaFinanceira {

    RECEITA(1,"Receita"),
    DESPESA(2,"Despesa");    
    
    private Integer id;
    private String naturezaFinanceira;

    private NaturezaFinanceira(Integer id, String naturezaFinanceira) {
        this.id = id;
        this.naturezaFinanceira = naturezaFinanceira;
    }

    public Integer getId() {
        return id;
    }

    public String getNaturezaFinanceira() {
        return naturezaFinanceira;
    }
}
