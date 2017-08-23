/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.war.builder.GrupoFiscalBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoFiscalBVConverter", forClass = GrupoFiscalBV.class)
public class GrupoFiscalBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof GrupoFiscal) {
                return new GrupoFiscalBV((GrupoFiscal) object);
            } else if (object instanceof GrupoFiscalBV) {
                return (GrupoFiscalBV) object;
            }
        }
        return new GrupoFiscalBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof GrupoFiscalBV) {
                String id = String.valueOf(((GrupoFiscalBV) object).getId());
                uic.getAttributes().put(id, (GrupoFiscalBV) object);
                return id;
            } else if (object instanceof GrupoFiscal) {
                String id = String.valueOf(((GrupoFiscal) object).getId());
                uic.getAttributes().put(id, (GrupoFiscal) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
