/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.war.builder.ContaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "contaBVConverter", forClass = ContaBV.class)
public class ContaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Conta) {
                return new ContaBV((Conta) object);
            } else if (object instanceof ContaBV) {
                return (ContaBV) object;
            }
        }
        return new ContaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ContaBV) {
                String id = String.valueOf(((ContaBV) object).getId());
                uic.getAttributes().put(id, (ContaBV) object);
                return id;
            } else if (object instanceof Conta) {
                String id = String.valueOf(((Conta) object).getId());
                uic.getAttributes().put(id, (Conta) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
