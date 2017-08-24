/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author Rafael Fernando Rauber
 */
public enum Totalizador {

    AVERAGE(new Long(1), new BundleUtil().getLabel("Media")),
    SUM(new Long(2), new BundleUtil().getLabel("Soma")),
    COUNT(new Long(3), new BundleUtil().getLabel("Quantidade"));

    private Long id;
    private String nome;

    private Totalizador(Long id, String nome) {
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
