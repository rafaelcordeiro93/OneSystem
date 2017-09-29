/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoPaisView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "paisConverter", forClass = Pais.class)
public class PaisConverter extends BasicConverter<Pais, SelecaoPaisView> implements Converter, Serializable {

    public PaisConverter() {
        super(Pais.class, SelecaoPaisView.class);
    }

}
