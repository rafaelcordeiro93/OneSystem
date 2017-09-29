/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.war.builder.EstadoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoEstadoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "estadoBVConverter", forClass = EstadoBV.class)
public class EstadoBVConverter extends BasicBVConverter<Estado, EstadoBV, SelecaoEstadoView> implements Converter, Serializable {

    public EstadoBVConverter() {
        super(Estado.class, EstadoBV.class, SelecaoEstadoView.class);
    }

}