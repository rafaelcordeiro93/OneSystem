/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rauber
 */
public enum SituacaoDeCheque {

    ABERTO("Aberto", "ABERTO"),
    CANCELADO("Cancelado", "CANCELADO"),
    DEVOLVIDO("Devolvido", "DEVOLVIDO"),
    DESCONTADO("Descontado", "DESCONTADO");

    private String tipoUnidade;
    private String tipoUnidadeUP;

    private SituacaoDeCheque(String tiponidade, String tipoUnidadeUP) {
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
