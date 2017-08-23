/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.war.builder.TipoReceitaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tipoReceitaBVConverter", forClass = TipoReceitaBV.class)
public class TipoReceitaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof TipoReceita) {
                return new TipoReceitaBV((TipoReceita) object);
            } else if (object instanceof TipoReceitaBV) {
                return (TipoReceitaBV) object;
            }
        }
        return new TipoReceitaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof TipoReceitaBV) {
                String id = String.valueOf(((TipoReceitaBV) object).getId());
                uic.getAttributes().put(id, (TipoReceitaBV) object);
                return id;
            } else if (object instanceof TipoReceita) {
                String id = String.valueOf(((TipoReceita) object).getId());
                uic.getAttributes().put(id, (TipoReceita) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
