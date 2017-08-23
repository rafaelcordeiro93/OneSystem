/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.war.builder.DespesaProvisionadaBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "despesaProvisionadaBVConverter", forClass = DespesaProvisionada.class)
public class DespesaProvisionadaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof DespesaProvisionada) {
                return new DespesaProvisionadaBV((DespesaProvisionada) object);
            } else if (object instanceof DespesaProvisionadaBV) {
                return (DespesaProvisionadaBV) object;
            }
        }
        return new DespesaProvisionadaBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof DespesaProvisionadaBV) {
                String id = String.valueOf(((DespesaProvisionadaBV) object).getId());
                uic.getAttributes().put(id, (DespesaProvisionadaBV) object);
                return id;
            } else if (object instanceof DespesaProvisionada) {
                String id = String.valueOf(((DespesaProvisionada) object).getId());
                uic.getAttributes().put(id, (DespesaProvisionada) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
