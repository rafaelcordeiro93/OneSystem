/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rauber
 */
public enum TipoFormaPagRec {

    A_VISTA("À vista"),
    A_PRAZO("À prazo");
    
    private String tipoFormaPagRec;

    private TipoFormaPagRec(String tipoFormaPagRec) {
        this.tipoFormaPagRec = tipoFormaPagRec;
    }

    public String getTipoFormaPagRec() {
        return tipoFormaPagRec;
    }
    
}
