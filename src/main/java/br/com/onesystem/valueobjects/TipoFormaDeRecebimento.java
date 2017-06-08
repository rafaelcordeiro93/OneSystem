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
public enum TipoFormaDeRecebimento {

    DINHEIRO(new Long(1), new BundleUtil().getLabel("Dinheiro")),
    CHEQUE(new Long(2), new BundleUtil().getLabel("Cheque")),
    CARTAO(new Long(3), new BundleUtil().getLabel("Boleto_De_Cartao")),
    A_FATURAR(new Long(4), new BundleUtil().getLabel("A_Faturar")),
    CREDITO(new Long(5), new BundleUtil().getLabel("Credito"));

    private Long id;
    private String nome;

    private TipoFormaDeRecebimento(Long id, String nome) {
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
