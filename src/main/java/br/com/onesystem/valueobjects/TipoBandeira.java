/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import java.util.Locale;

/**
 *
 * @author Rafael Fernando Rauber
 */
public enum TipoBandeira {

    BRASIL(1, "Brasil", new Locale("pt", "BR")),
    PARAGUAI(2, "Paraguai", new Locale("es", "PY")),
    ESTADOS_UNIDOS(3, "Estados Unidos", Locale.US),
    INGLATERRA(4, "Inglaterra", Locale.ENGLISH),
    FRANCA(5, "França", Locale.FRANCE),
    ALEMANHA(6, "Alemanha", Locale.GERMANY);

    private final Integer id;
    private final String nome;
    private final Locale local;

    TipoBandeira(Integer id, String nome, Locale local) {
        this.id = id;
        this.nome = nome;
        this.local = local;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Locale getLocal() {
        return local;
    }

}
