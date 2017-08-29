/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author Rafael
 */
public enum TipoLayout {
    
    ORCAMENTO(new Long(1), new BundleUtil().getLabel("Orcamento")),
    COMANDA(new Long(2), new BundleUtil().getLabel("Comanda")),
    CONDICIONAL(new Long(3), new BundleUtil().getLabel("Condicional")),
    TITULO(new Long(4), new BundleUtil().getLabel("Titulo")),
    NOTA_EMITIDA(new Long(5), new BundleUtil().getLabel("Nota_Emitida"));
    
    private Long id;
    private String nome;

    private TipoLayout(Long id, String nome) {
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
