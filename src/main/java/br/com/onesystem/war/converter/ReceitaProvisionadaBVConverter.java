/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.war.builder.ReceitaProvisionadaBV;
import br.com.onesystem.war.service.ReceitaProvisionadaService;
import java.io.Serializable;
import java.util.List;
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
        if (value != null && value.trim().length() > 0) {
            try {
                List<ReceitaProvisionada> lista = new ReceitaProvisionadaService().buscarReceitaProvisionadas();
                for (ReceitaProvisionada receitaProvisionada : lista) {
                    if (receitaProvisionada.getId().equals(new Long(value))) {
                        return new ReceitaProvisionadaBV(receitaProvisionada);
                    }
                }
                return new ReceitaProvisionadaBV();
            } catch (Exception e) {
                return new ReceitaProvisionadaBV();
            }
        } else {
            return new ReceitaProvisionadaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ReceitaProvisionadaBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
