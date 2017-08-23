/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.war.builder.CidadeBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cidadeBVConverter", forClass = CidadeBV.class)
public class CidadeBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Cidade) {
                return new CidadeBV((Cidade) object);
            } else if (object instanceof CidadeBV) {
                return (CidadeBV) object;
            }
        }
        return new CidadeBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof CidadeBV) {
                String id = String.valueOf(((CidadeBV) object).getId());
                uic.getAttributes().put(id, (CidadeBV) object);
                return id;
            } else if (object instanceof Cidade) {
                String id = String.valueOf(((Cidade) object).getId());
                uic.getAttributes().put(id, (Cidade) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
