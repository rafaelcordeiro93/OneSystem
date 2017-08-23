/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.war.builder.PaisBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "paisBVConverter", forClass = PaisBV.class)
public class PaisBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof PaisBV) {
                return (PaisBV) object;
            } else if (object instanceof Pais) {
                Pais pais = (Pais) object;
                return new PaisBV(pais);
            }
        }
        return new PaisBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof PaisBV) {
                String id = String.valueOf(((PaisBV) object).getId());
                uic.getAttributes().put(id, (PaisBV) object);
                return id;
            } else if (object instanceof Pais) {
                String id = String.valueOf(((Pais) object).getId());
                uic.getAttributes().put(id, (Pais) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
