/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.war.builder.GrupoBV;
import br.com.onesystem.war.service.impl.BasicBVConverter;
import br.com.onesystem.war.view.selecao.SelecaoGrupoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoBVConverter", forClass = GrupoBV.class)
public class GrupoBVConverter extends BasicBVConverter<Grupo, GrupoBV, SelecaoGrupoView> implements Converter, Serializable {

    public GrupoBVConverter() {
        super(Grupo.class, GrupoBV.class, SelecaoGrupoView.class);
    }

}