/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author Rauber
 */
public enum EstadoDeCheque {

    ABERTO(new Long(1), new BundleUtil().getLabel("ABERTO")),
    CANCELADO(new Long(2), new BundleUtil().getLabel("CANCELADO")),
    DEVOLVIDO(new Long(3), new BundleUtil().getLabel("DEVOLVIDO")),
    DESCONTADO(new Long(4), new BundleUtil().getLabel("DESCONTADO"));

    private Long id;
    private String nome;

    private EstadoDeCheque(Long id, String nome) {
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
