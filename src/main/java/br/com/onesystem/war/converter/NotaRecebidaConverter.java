/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.NotaRecebida;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "notaRecebidaConverter", forClass = NotaRecebida.class)
public class NotaRecebidaConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof NotaRecebida) {
                return (NotaRecebida) object;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof NotaRecebida) {
                String id = String.valueOf(((NotaRecebida) object).getId());
                uic.getAttributes().put(id, (NotaRecebida) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
