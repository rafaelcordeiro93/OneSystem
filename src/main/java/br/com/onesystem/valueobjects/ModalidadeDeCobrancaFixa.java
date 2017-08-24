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
public enum ModalidadeDeCobrancaFixa {

    DESPESA_EVENTUAL(new Long(1), new BundleUtil().getLabel("Despesa_Eventual")),
    DESPESA_PROVISIONADA(new Long(2), new BundleUtil().getLabel("Despesa_Provisionada")),
    RECEITA_EVENTUAL(new Long(3), new BundleUtil().getLabel("Receita_Eventual")),
    RECEITA_PROVISIONADA(new Long(4), new BundleUtil().getLabel("Receita_Provisionada"));

    private Long id;
    private String nome;

    private ModalidadeDeCobrancaFixa(Long id, String nome) {
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
