/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.war.builder.MarcaBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoMarcaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "marcaBVConverter", forClass = MarcaBV.class)
public class MarcaBVConverter extends BasicBVConverter<Marca, MarcaBV, SelecaoMarcaView> implements Converter, Serializable {

    public MarcaBVConverter() {
        super(Marca.class, MarcaBV.class, SelecaoMarcaView.class);
    }

}
