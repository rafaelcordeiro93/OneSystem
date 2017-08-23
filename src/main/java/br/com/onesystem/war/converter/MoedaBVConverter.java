/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.war.builder.MoedaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "moedaBVConverter", forClass = MoedaBV.class)
public class MoedaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Moeda) {
                return new MoedaBV((Moeda) object);
            } else if (object instanceof MoedaBV) {
                return (MoedaBV) object;
            }
        }
        return new MoedaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof MoedaBV) {
                String id = String.valueOf(((MoedaBV) object).getId());
                uic.getAttributes().put(id, (MoedaBV) object);
                return id;
            } else if (object instanceof Moeda) {
                String id = String.valueOf(((Moeda) object).getId());
                uic.getAttributes().put(id, (Moeda) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
