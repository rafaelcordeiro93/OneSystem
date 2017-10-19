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
public enum TipoItem {

    MERCADORIA(new BundleUtil().getLabel("Mercadoria")),
    PRODUTO(new BundleUtil().getLabel("Produto")),
    MATERIA_PRIMA(new BundleUtil().getLabel("Materia_Prima")),
    SERVICO(new BundleUtil().getLabel("Servico")),
    IMOBILIZADO(new BundleUtil().getLabel("Imobilizado"));

    private String nome;

    private TipoItem(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
