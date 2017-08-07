/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.GrupoBV;
import br.com.onesystem.war.service.GrupoService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoBVConverter", forClass = GrupoBV.class)
public class GrupoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Grupo> lista = new GrupoService().buscarGrupos();
                if (StringUtils.containsLetter(value)) {
                    for (Grupo grupo : lista) {
                        if (grupo.getNome().equals(value)) {
                            return new GrupoBV(grupo);
                        }
                    }
                } else {
                    for (Grupo grupo : lista) {
                        if (grupo.getId().equals(new Long(value))) {
                            return new GrupoBV(grupo);
                        }
                    }
                }
                return new GrupoBV();
            } catch (Exception e) {
                return new GrupoBV();
            }
        } else {
            return new GrupoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((GrupoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
