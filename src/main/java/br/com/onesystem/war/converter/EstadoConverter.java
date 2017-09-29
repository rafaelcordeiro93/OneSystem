/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoEstadoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "estadoConverter", forClass = Estado.class)
public class EstadoConverter extends BasicConverter<Estado, SelecaoEstadoView> implements Converter, Serializable {

    public EstadoConverter() {
        super(Estado.class, SelecaoEstadoView.class);
    }
}
