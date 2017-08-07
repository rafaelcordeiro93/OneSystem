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
public enum OperadorDeBusca {

    AND(new Long(1), new BundleUtil().getLabel("e"), "and"),
    OR(new Long(2), new BundleUtil().getLabel("ou"), "or");

    private Long id;
    private String nome;
    private String operador;

    private OperadorDeBusca(Long id, String nome, String operador) {
        this.id = id;
        this.nome = nome;
        this.operador = operador;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getOperador() {
        return operador;
    }

}
