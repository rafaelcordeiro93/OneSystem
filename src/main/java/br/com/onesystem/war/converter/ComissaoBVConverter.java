/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.war.builder.ComissaoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "comissaoBVConverter", forClass = ComissaoBV.class)
public class ComissaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Comissao) {
                return new ComissaoBV((Comissao) object);
            } else if (object instanceof ComissaoBV) {
                return (ComissaoBV) object;
            }
        }
        return new ComissaoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ComissaoBV) {
                String id = String.valueOf(((ComissaoBV) object).getId());
                uic.getAttributes().put(id, (ComissaoBV) object);
                return id;
            } else if (object instanceof Comissao) {
                String id = String.valueOf(((Comissao) object).getId());
                uic.getAttributes().put(id, (Comissao) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
