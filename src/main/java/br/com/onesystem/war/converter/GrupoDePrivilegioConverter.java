/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoDePrivilegio;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoDePrivilegioConverter", forClass = GrupoDePrivilegio.class)
public class GrupoDePrivilegioConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof GrupoDePrivilegio) {
                return (GrupoDePrivilegio) object;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof GrupoDePrivilegio) {
                String id = String.valueOf(((GrupoDePrivilegio) object).getId());
                uic.getAttributes().put(id, (GrupoDePrivilegio) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
