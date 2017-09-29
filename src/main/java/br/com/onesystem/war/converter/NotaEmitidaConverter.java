/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoNotaEmitidaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "notaEmitidaConverter", forClass = NotaEmitida.class)
public class NotaEmitidaConverter extends BasicConverter<NotaEmitida, SelecaoNotaEmitidaView> implements Converter, Serializable {

    public NotaEmitidaConverter() {
        super(NotaEmitida.class, SelecaoNotaEmitidaView.class);
    }

}
