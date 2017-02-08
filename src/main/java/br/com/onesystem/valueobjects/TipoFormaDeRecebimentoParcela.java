/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.onesystem.valueobjects;

/**
 *
 * @author Rafael Fernando Rauber
 */
public enum TipoFormaDeRecebimentoParcela {

    CHEQUE(new Long(1)),
    CARTAO(new Long(2)),
    TITULO(new Long(3));
    
    private Long id;
    
    private TipoFormaDeRecebimentoParcela(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }    

}
