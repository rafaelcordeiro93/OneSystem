/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.war.builder.ListaDePrecoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "listaDePrecoBVConverter", forClass = ListaDePrecoBV.class)
public class ListaDePrecoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof ListaDePreco) {
                return new ListaDePrecoBV((ListaDePreco) object);
            } else if (object instanceof ListaDePrecoBV) {
                return (ListaDePrecoBV) object;
            }
        }
        return new ListaDePrecoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ListaDePrecoBV) {
                String id = String.valueOf(((ListaDePrecoBV) object).getId());
                uic.getAttributes().put(id, (ListaDePrecoBV) object);
                return id;
            } else if (object instanceof ListaDePreco) {
                String id = String.valueOf(((ListaDePreco) object).getId());
                uic.getAttributes().put(id, (ListaDePreco) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
