/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rauber
 */
public enum TipoItem {

    MERCADORIA("Mercaderia"),
    PRODUTO("Producto"),
    MATERIA_PRIMA("Materia Prima"),
    SERVICO("Servicio"),
    IMOBILIZADO("Propriedad");
    
    private String tipoItem;

    private TipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }

    public String getTipoItem() {
        return tipoItem;
    }
}
