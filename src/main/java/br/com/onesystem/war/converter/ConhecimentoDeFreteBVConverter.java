/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.war.builder.ConhecimentoDeFreteBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoConhecimentoDeFreteView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "conhecimentoDeFreteBVConverter", forClass = ConhecimentoDeFrete.class)
public class ConhecimentoDeFreteBVConverter extends BasicBVConverter<ConhecimentoDeFrete, ConhecimentoDeFreteBV, SelecaoConhecimentoDeFreteView> implements Converter, Serializable {

    public ConhecimentoDeFreteBVConverter() {
        super(ConhecimentoDeFrete.class, ConhecimentoDeFreteBV.class, SelecaoConhecimentoDeFreteView.class);
    }

}