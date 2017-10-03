/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author Rafael Cordeiro
 */
public enum DetalhamentoDeItem {

    NORMAL(new Long(1), new BundleUtil().getLabel("Normal")),
    LOTES(new Long(2), new BundleUtil().getLabel("Lotes"));

    private Long id;
    private String nome;

    private DetalhamentoDeItem(Long id, String nome) {
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
