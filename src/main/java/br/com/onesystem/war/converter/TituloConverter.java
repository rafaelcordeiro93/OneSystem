/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Titulo;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoTituloView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tituloConverter", forClass = Titulo.class)
public class TituloConverter extends BasicConverter<Titulo, SelecaoTituloView> implements Converter, Serializable {

    public TituloConverter() {
        super(Titulo.class, SelecaoTituloView.class);
    }
}