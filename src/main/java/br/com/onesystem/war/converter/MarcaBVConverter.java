/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.war.builder.MarcaBV;
import br.com.onesystem.war.builder.MarcaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "marcaBVConverter", forClass = MarcaBV.class)
public class MarcaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Marca) {
                return new MarcaBV((Marca) object);
            } else if (object instanceof MarcaBV) {
                return (MarcaBV) object;
            }
        }
        return new MarcaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof MarcaBV) {
                String id = String.valueOf(((MarcaBV) object).getId());
                uic.getAttributes().put(id, (MarcaBV) object);
                return id;
            } else if (object instanceof Marca) {
                String id = String.valueOf(((Marca) object).getId());
                uic.getAttributes().put(id, (Marca) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
