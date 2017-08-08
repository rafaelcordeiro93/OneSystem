/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.EstadoBV;
import br.com.onesystem.war.service.EstadoService;
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
@FacesConverter(value = "estadoBVConverter", forClass = EstadoBV.class)
public class EstadoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Estado> lista = new EstadoService().buscarEstados();
                if (StringUtils.containsLetter(value)) {
                    for (Estado estado : lista) {
                        if (estado.getNome().equals(value)) {
                            return new EstadoBV(estado);
                        }
                    }
                } else {
                    for (Estado estado : lista) {
                        if (estado.getId().equals(new Long(value))) {
                            return new EstadoBV(estado);
                        }
                    }
                }
                return new EstadoBV();
            } catch (Exception e) {
                return new EstadoBV();
            }
        } else {
            return new EstadoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((EstadoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
