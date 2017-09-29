/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.war.service.impl.BasicConverter;
import br.com.onesystem.war.view.selecao.SelecaoGrupoView;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoConverter", forClass = Grupo.class)
public class GrupoConverter extends BasicConverter<Grupo, SelecaoGrupoView> implements Converter, Serializable {

    public GrupoConverter() {
        super(Grupo.class, SelecaoGrupoView.class);
    }
}
