/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.war.builder.CepBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cepBVConverter", forClass = CepBV.class)
public class CepBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Cep) {
                return new CepBV((Cep) object);
            } else if (object instanceof CepBV) {
                return (CepBV) object;
            }
        }
        return new CepBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof CepBV) {
                String id = String.valueOf(((CepBV) object).getId());
                uic.getAttributes().put(id, (CepBV) object);
                return id;
            } else if (object instanceof Cep) {
                String id = String.valueOf(((Cep) object).getId());
                uic.getAttributes().put(id, (Cep) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
