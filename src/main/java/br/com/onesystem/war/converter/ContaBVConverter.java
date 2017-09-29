/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.war.builder.ContaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoContaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "contaBVConverter", forClass = ContaBV.class)
public class ContaBVConverter extends BasicBVConverter<Conta, ContaBV, SelecaoContaView> implements Converter, Serializable {

    public ContaBVConverter() {
        super(Conta.class, ContaBV.class, SelecaoContaView.class);
    }

}