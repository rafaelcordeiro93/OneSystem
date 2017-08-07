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
public enum OperacaoFinanceira {

    ENTRADA(new Long(1), new BundleUtil().getLabel("ENTRADA")),
    SAIDA(new Long(2), new BundleUtil().getLabel("SAIDA")),
    SEM_ALTERACAO(new Long(3), new BundleUtil().getLabel("SEM_ALTERACAO"));

    private Long id;
    private String nome;
    private String tipoUnidadeUP;

    private OperacaoFinanceira(Long id, String nome) {
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
