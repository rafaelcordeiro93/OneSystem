/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Motivo;
import br.com.onesystem.war.builder.MotivoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoMotivoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "motivoBVConverter", forClass = MotivoBV.class)
public class MotivoBVConverter extends BasicBVConverter<Motivo, MotivoBV, SelecaoMotivoView> implements Converter, Serializable {

    public MotivoBVConverter() {
        super(Motivo.class, MotivoBV.class, SelecaoMotivoView.class);
    }

}
