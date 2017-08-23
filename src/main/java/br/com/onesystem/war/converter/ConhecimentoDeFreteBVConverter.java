/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "conhecimentoDeFreteBVConverter", forClass = ConhecimentoDeFrete.class)
public class ConhecimentoDeFreteBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof ConhecimentoDeFrete) {
                return (ConhecimentoDeFrete) object;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ConhecimentoDeFrete) {
                String id = String.valueOf(((ConhecimentoDeFrete) object).getId());
                uic.getAttributes().put(id, (ConhecimentoDeFrete) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
