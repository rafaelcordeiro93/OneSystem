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
public enum TipoDeBusca {

    CONTENDO(new Long(1), new BundleUtil().getLabel("Contendo")),
    DIFERENTE_DE(new Long(2), new BundleUtil().getLabel("Diferente_de")),
    IGUAL_A(new Long(3), new BundleUtil().getLabel("Igual_a")),
    INICIANDO(new Long(4), new BundleUtil().getLabel("Iniciando")),
    MAIOR_QUE(new Long(5), new BundleUtil().getLabel("Maior_que")),
    MAIOR_OU_IGUAL_A(new Long(6), new BundleUtil().getLabel("Maior_ou_igual_a")),
    MENOR_QUE(new Long(7), new BundleUtil().getLabel("Menor_que")),
    MENOR_OU_IGUAL_A(new Long(8), new BundleUtil().getLabel("Menor_ou_igual_a")),
    TERMINANDO(new Long(9), new BundleUtil().getLabel("Terminando"));

    private Long id;
    private String nome;

    private TipoDeBusca(Long id, String nome) {
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
