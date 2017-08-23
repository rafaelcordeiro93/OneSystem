/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.war.builder.CfopBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cfopBVConverter", forClass = CfopBV.class)
public class CfopBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Cfop) {
                return new CfopBV((Cfop) object);
            } else if (object instanceof CfopBV) {
                return (CfopBV) object;
            }
        }
        return new CfopBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof CfopBV) {
                String id = String.valueOf(((CfopBV) object).getId());
                uic.getAttributes().put(id, (CfopBV) object);
                return id;
            } else if (object instanceof Cfop) {
                String id = String.valueOf(((Cfop) object).getId());
                uic.getAttributes().put(id, (Cfop) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
