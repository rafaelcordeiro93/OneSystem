/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Motivo;
import br.com.onesystem.war.builder.MotivoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "motivoBVConverter", forClass = MotivoBV.class)
public class MotivoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Motivo) {
                return new MotivoBV((Motivo) object);
            } else if (object instanceof MotivoBV) {
                return (MotivoBV) object;
            }
        }
        return new MotivoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof MotivoBV) {
                String id = String.valueOf(((MotivoBV) object).getId());
                uic.getAttributes().put(id, (MotivoBV) object);
                return id;
            } else if (object instanceof Motivo) {
                String id = String.valueOf(((Motivo) object).getId());
                uic.getAttributes().put(id, (Motivo) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
