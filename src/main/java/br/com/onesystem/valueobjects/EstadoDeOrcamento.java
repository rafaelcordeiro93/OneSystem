/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author rauber
 */
public enum EstadoDeOrcamento {
    
    EM_DEFINICAO(new BundleUtil().getLabel("EM_DEFINICAO")),
    EM_APROVACAO(new BundleUtil().getLabel("EM_APROVACAO")),
    APROVADO(new BundleUtil().getLabel("APROVADO")),
    REPROVADO(new BundleUtil().getLabel("REPROVADO")),
    CANCELADO(new BundleUtil().getLabel("CANCELADO")),
    EFETIVADO(new BundleUtil().getLabel("EFETIVADO"));
    
    private final String nome;

    private EstadoDeOrcamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    
}
