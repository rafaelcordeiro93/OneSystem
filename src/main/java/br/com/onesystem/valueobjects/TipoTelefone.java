/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rauber
 */
public enum TipoTelefone {

    CELULAR("Celular"),
    CASA("Casa"),
    TRABALHO("Trabalho"),
    FAX_DO_TRABALHO("Fax do trabalho"),
    FAX_DE_CASA("Fax de casa"),
    PAGER("Pager"),
    OUTROS("Outros");
    private String tipoTelefono;

    private TipoTelefone(String tipoTelefone) {
        this.tipoTelefono = tipoTelefone;
    }

    public String getTipoTelefono() {
        return tipoTelefono;
    }
}
