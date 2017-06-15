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
public enum EstadoDeLancamento {

    CANCELADO(new BundleUtil().getLabel("CANCELADO")),
    EFETIVADO(new BundleUtil().getLabel("EFETIVADO"));

    private String nome;

    private EstadoDeLancamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
