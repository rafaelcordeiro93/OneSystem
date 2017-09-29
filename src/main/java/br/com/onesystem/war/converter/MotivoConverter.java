/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Motivo;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoMotivoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael Cordeiro
 */
@FacesConverter(value = "motivoConverter", forClass = Motivo.class)
public class MotivoConverter extends BasicConverter<Motivo, SelecaoMotivoView> implements Converter, Serializable {

    public MotivoConverter() {
        super(Motivo.class, SelecaoMotivoView.class);
    }

}
