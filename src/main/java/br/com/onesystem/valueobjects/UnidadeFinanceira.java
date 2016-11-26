/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rauber
 */
public enum UnidadeFinanceira {

    ENTRADA("Entrada", "ENTRADA"),
    SAIDA("Saída", "SAIDA"),
    SEM_ALTERACAO("Sem Alteracao", "SEM_ALTERACAO");
    private String tipoUnidade;
    private String tipoUnidadeUP;
    
    private UnidadeFinanceira(String tiponidade, String tipoUnidadeUP) {
        this.tipoUnidade = tiponidade;
        this.tipoUnidadeUP = tipoUnidadeUP;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public String getTipoUnidadeUP() {
        return tipoUnidadeUP;
    }
}
