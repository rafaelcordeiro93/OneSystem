/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.war.builder.FormaDeRecebimentoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "formaDeRecebimentoBVConverter", forClass = FormaDeRecebimento.class)
public class FormaDeRecebimentoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof FormaDeRecebimento) {
                return new FormaDeRecebimentoBV((FormaDeRecebimento) object);
            } else if (object instanceof FormaDeRecebimentoBV) {
                return (FormaDeRecebimentoBV) object;
            }
        }
        return new FormaDeRecebimentoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof FormaDeRecebimentoBV) {
                String id = String.valueOf(((FormaDeRecebimentoBV) object).getId());
                uic.getAttributes().put(id, (FormaDeRecebimentoBV) object);
                return id;
            } else if (object instanceof FormaDeRecebimento) {
                String id = String.valueOf(((FormaDeRecebimento) object).getId());
                uic.getAttributes().put(id, (FormaDeRecebimento) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
