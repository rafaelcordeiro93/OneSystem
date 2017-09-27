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
public enum EspecieDeNotaFiscal {

    NOTA_FISCAL_DE_PRESTACAO_DE_SERVICOS(new Long(1), new BundleUtil().getLabel("Nota_Fiscal_De_Prestacao_De_Servicos")),
    NOTA_FISCAL(new Long(2), new BundleUtil().getLabel("Nota_Fiscal")),
    NOTA_FISCAL_FATURA(new Long(3), new BundleUtil().getLabel("Nota_Fiscal_Fatura")),
    NOTA_FISCAL_CONSUMIDOR(new Long(4), new BundleUtil().getLabel("Nota_Fiscal_Consumidor")),
    NOTA_FISCAL_ELETRONICA(new Long(5), new BundleUtil().getLabel("Nota_Fiscal_Eletronica")),
    NOTA_FISCAL_SERIE_D(new Long(6), new BundleUtil().getLabel("Nota_Fiscal_Serie_D")), 
    NOTA_FISCAL_CONSUMIDOR_ELETRONICA(new Long(7), new BundleUtil().getLabel("Nota_Fiscal_Ao_Consumidor_Eletronica"));

    private Long id;
    private String nome;

    private EspecieDeNotaFiscal(Long id, String nome) {
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
