/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author Rafael Cordeiro
 */
public enum ModeloDeNotaFiscal {

    NENHUM(new Long(1), new BundleUtil().getLabel("Nenhum")),
    NOTA_FORMULARIO_CONTINUO(new Long(2), new BundleUtil().getLabel("Nota_Formulario_Continuo")),
    NOTA_FISCAL_AO_CONSUMIDOR(new Long(3), new BundleUtil().getLabel("Nota_Fiscal_Consumidor")),
    NOTA_FISCAL_DE_COMUNICACAO(new Long(4), new BundleUtil().getLabel("Nota_Fiscal_De_Comunicacao")),
    NOTA_FISCAL_ELETRONICA(new Long(5), new BundleUtil().getLabel("Nota_Fiscal_Eletronica")),
    NOTA_FISCAL_AO_CONSUMIDOR_ELETRONICA(new Long(6), new BundleUtil().getLabel("Nota_Fiscal_Ao_Consumidor_Eletronica"));

    private Long id;
    private String nome;

    private ModeloDeNotaFiscal(Long id, String nome) {
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
