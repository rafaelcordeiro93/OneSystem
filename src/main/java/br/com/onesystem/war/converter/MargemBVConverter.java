/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.war.builder.GrupoDeMargemBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoMargemView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "margemBVConverter", forClass = GrupoDeMargemBV.class)
public class MargemBVConverter extends BasicBVConverter<Margem, GrupoDeMargemBV, SelecaoMargemView> implements Converter, Serializable {

    public MargemBVConverter() {
        super(Margem.class, GrupoDeMargemBV.class, SelecaoMargemView.class);
    }

}
