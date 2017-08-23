/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "contaDeEstoqueBVConverter", forClass = ContaDeEstoqueBV.class)
public class ContaDeEstoqueBVConverter implements Converter, Serializable {

     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof ContaDeEstoque) {
                return new ContaDeEstoqueBV((ContaDeEstoque) object);
            } else if (object instanceof ContaDeEstoqueBV) {
                return (ContaDeEstoqueBV) object;
            }
        }
        return new ContaDeEstoqueBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ContaDeEstoqueBV) {
                String id = String.valueOf(((ContaDeEstoqueBV) object).getId());
                uic.getAttributes().put(id, (ContaDeEstoqueBV) object);
                return id;
            } else if (object instanceof ContaDeEstoque) {
                String id = String.valueOf(((ContaDeEstoque) object).getId());
                uic.getAttributes().put(id, (ContaDeEstoque) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
