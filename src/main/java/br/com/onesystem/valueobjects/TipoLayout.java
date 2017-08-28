/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author Rafael
 */
public enum TipoLayout {
    
    ORCAMENTO(new Long(1), "Orcamento"),
    COMANDA(new Long(2), "Comanda"),
    CONDICIONAL(new Long(3), "Condicional"),
    TITULO(new Long(4), "Titulo");
    
    private Long id;
    private String nome;

    private TipoLayout(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
}
