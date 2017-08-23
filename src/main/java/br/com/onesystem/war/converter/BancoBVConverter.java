/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.war.builder.BancoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "bancoBVConverter", forClass = BancoBV.class)
public class BancoBVConverter implements Converter, Serializable {

      @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Banco) {
                return new BancoBV((Banco) object);
            } else if (object instanceof BancoBV) {
                return (BancoBV) object;
            }
        }
        return new BancoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof BancoBV) {
                String id = String.valueOf(((BancoBV) object).getId());
                uic.getAttributes().put(id, (BancoBV) object);
                return id;
            } else if (object instanceof Banco) {
                String id = String.valueOf(((Banco) object).getId());
                uic.getAttributes().put(id, (Banco) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
