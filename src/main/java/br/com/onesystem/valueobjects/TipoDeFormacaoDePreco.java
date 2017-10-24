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
public enum TipoDeFormacaoDePreco {

    MARKUP(new Long(1), new BundleUtil().getLabel("Markup")),
    MARGEM_BRUTA(new Long(2), new BundleUtil().getLabel("Margem_Bruta"));

    private Long id;
    private String nome;

    private TipoDeFormacaoDePreco(Long id, String nome) {
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
