/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.war.builder.DepositoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoDepositoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "depositoBVConverter", forClass = Deposito.class)
public class DepositoBVConverter extends BasicBVConverter<Deposito, DepositoBV, SelecaoDepositoView> implements Converter, Serializable {

    public DepositoBVConverter() {
        super(Deposito.class, DepositoBV.class, SelecaoDepositoView.class);
    }

}