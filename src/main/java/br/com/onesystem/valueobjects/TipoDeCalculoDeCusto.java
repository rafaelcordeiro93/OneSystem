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
public enum TipoDeCalculoDeCusto {

    ULTIMO_CUSTO(new Long(1), new BundleUtil().getLabel("Ultimo_Custo")),
    CUSTO_MEDIO(new Long(2), new BundleUtil().getLabel("Custo_Medio"));

    private Long id;
    private String nome;

    private TipoDeCalculoDeCusto(Long id, String nome) {
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
