/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.war.builder.ReceitaProvisionadaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "receitaProvisionadaBVConverter", forClass = ReceitaProvisionada.class)
public class ReceitaProvisionadaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof ReceitaProvisionada) {
                return new ReceitaProvisionadaBV((ReceitaProvisionada) object);
            } else if (object instanceof ReceitaProvisionadaBV) {
                return (ReceitaProvisionadaBV) object;
            }
        }
        return new ReceitaProvisionadaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof ReceitaProvisionadaBV) {
                String id = String.valueOf(((ReceitaProvisionadaBV) object).getId());
                uic.getAttributes().put(id, (ReceitaProvisionadaBV) object);
                return id;
            } else if (object instanceof ReceitaProvisionada) {
                String id = String.valueOf(((ReceitaProvisionada) object).getId());
                uic.getAttributes().put(id, (ReceitaProvisionada) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
