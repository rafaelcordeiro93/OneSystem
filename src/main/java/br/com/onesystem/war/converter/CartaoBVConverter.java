/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.war.builder.CartaoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cartaoBVConverter", forClass = CartaoBV.class)
public class CartaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Cartao) {
                return new CartaoBV((Cartao) object);
            } else if (object instanceof CartaoBV) {
                return (CartaoBV) object;
            }
        }
        return new CartaoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof CartaoBV) {
                String id = String.valueOf(((CartaoBV) object).getId());
                uic.getAttributes().put(id, (CartaoBV) object);
                return id;
            } else if (object instanceof Cartao) {
                String id = String.valueOf(((Cartao) object).getId());
                uic.getAttributes().put(id, (Cartao) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
