/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.war.builder.MoedaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoMoedaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "moedaBVConverter", forClass = MoedaBV.class)
public class MoedaBVConverter extends BasicBVConverter<Moeda, MoedaBV, SelecaoMoedaView> implements Converter, Serializable {

    public MoedaBVConverter() {
        super(Moeda.class, MoedaBV.class, SelecaoMoedaView.class);
    }
}
