/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Grupo;
import br.com.onesystem.war.builder.GrupoBV;
import java.io.Serializable;
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
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Grupo) {
                return new GrupoBV((Grupo) object);
            } else if (object instanceof GrupoBV) {
                return (GrupoBV) object;
            }
        }
        return new GrupoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof GrupoBV) {
                String id = String.valueOf(((GrupoBV) object).getId());
                uic.getAttributes().put(id, (GrupoBV) object);
                return id;
            } else if (object instanceof Grupo) {
                String id = String.valueOf(((Grupo) object).getId());
                uic.getAttributes().put(id, (Grupo) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
