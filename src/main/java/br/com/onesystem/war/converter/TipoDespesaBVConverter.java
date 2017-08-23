/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.war.builder.TipoDespesaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "tipoDespesaBVConverter", forClass = TipoDespesaBV.class)
public class TipoDespesaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof TipoDespesa) {
                return new TipoDespesaBV((TipoDespesa) object);
            } else if (object instanceof TipoDespesaBV) {
                return (TipoDespesaBV) object;
            }
        }
        return new TipoDespesaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof TipoDespesaBV) {
                String id = String.valueOf(((TipoDespesaBV) object).getId());
                uic.getAttributes().put(id, (TipoDespesaBV) object);
                return id;
            } else if (object instanceof TipoDespesa) {
                String id = String.valueOf(((TipoDespesa) object).getId());
                uic.getAttributes().put(id, (TipoDespesa) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
