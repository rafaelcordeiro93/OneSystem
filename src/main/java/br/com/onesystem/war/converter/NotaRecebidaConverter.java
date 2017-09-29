/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoNotaRecebidaView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "notaRecebidaConverter", forClass = NotaRecebida.class)
public class NotaRecebidaConverter extends BasicConverter<NotaRecebida, SelecaoNotaRecebidaView> implements Converter, Serializable {

    public NotaRecebidaConverter() {
        super(NotaRecebida.class, SelecaoNotaRecebidaView.class);
    }

}
