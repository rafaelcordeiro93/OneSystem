/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.war.builder.PaisBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoPaisView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "paisBVConverter", forClass = PaisBV.class)
public class PaisBVConverter extends BasicBVConverter<Pais, PaisBV, SelecaoPaisView> implements Converter, Serializable {

    public PaisBVConverter() {
        super(Pais.class, PaisBV.class, SelecaoPaisView.class);
    }
}