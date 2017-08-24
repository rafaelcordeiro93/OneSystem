/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 * Deve controlar a situação da cobranca e cobranca fixa.
 *
 * @date 10/08/2017
 * @author Rafael Fernando Rauber
 */
public enum SituacaoDeCobranca {

    PAGO(new Long(1), new BundleUtil().getLabel("Pago")),
    ABERTO(new Long(2), new BundleUtil().getLabel("Aberto"));

    private Long id;
    private String nome;

    private SituacaoDeCobranca(Long id, String nome) {
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
