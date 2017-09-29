/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoChequeView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "chequeConverter", forClass = Cheque.class)
public class ChequeConverter extends BasicConverter<Cheque, SelecaoChequeView> implements Converter, Serializable {

    public ChequeConverter() {
        super(Cheque.class, SelecaoChequeView.class);
    }
}
