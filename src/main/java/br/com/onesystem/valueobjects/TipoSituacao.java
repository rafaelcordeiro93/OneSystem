/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rauber
 */
public enum TipoSituacao {

    ABERTO("Aberto", "ABERTO"),
    CANCELADO("Cancelado", "CANCELADO"),
    DISPONIVEL("Disponível", "DISPONIVEL"),
    PARCIAL("Parcial", "PARCIAL"),
    QUITADO("Quitado", "QUITADO");

    private String tipoUnidade;
    private String tipoUnidadeUP;

    private TipoSituacao(String tiponidade, String tipoUnidadeUP) {
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
