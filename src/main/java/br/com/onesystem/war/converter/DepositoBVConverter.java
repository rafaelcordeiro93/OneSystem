/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.war.builder.DepositoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "depositoBVConverter", forClass = Deposito.class)
public class DepositoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Deposito) {
                return new DepositoBV((Deposito) object);
            } else if (object instanceof DepositoBV) {
                return (DepositoBV) object;
            }
        }
        return new DepositoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof DepositoBV) {
                String id = String.valueOf(((DepositoBV) object).getId());
                uic.getAttributes().put(id, (DepositoBV) object);
                return id;
            } else if (object instanceof Deposito) {
                String id = String.valueOf(((Deposito) object).getId());
                uic.getAttributes().put(id, (Deposito) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
