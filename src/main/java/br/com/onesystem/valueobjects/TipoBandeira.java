/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rafael Fernando Rauber
 */
public enum TipoBandeira {

    BRASIL(1,"Brasil"),
    PARAGUAI(2,"Paraguai"),
    ESTADOS_UNIDOS(3,"Estados Unidos"),
    INGLATERRA(4, "Inglaterra"),
    FRANCA(5,"França"),
    ALEMANHA(6,"Alemanha");

    private Integer id;
    private String nome;

    TipoBandeira(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    

}
