/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.valueobjects.EspecieDeNotaFiscal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "especieDeNotaFiscalConverter", forClass = EspecieDeNotaFiscal.class)
public class EspecieDeNotaFiscalConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof EspecieDeNotaFiscal) {
                return (EspecieDeNotaFiscal) object;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof EspecieDeNotaFiscal) {
                String id = String.valueOf(((EspecieDeNotaFiscal) object).getId());
                uic.getAttributes().put(id, (EspecieDeNotaFiscal) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
