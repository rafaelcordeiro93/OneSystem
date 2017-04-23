/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.MoedaBV;
import br.com.onesystem.war.service.MoedaService;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "moedaBVConverter", forClass = MoedaBV.class)
public class MoedaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            MoedaService service = new MoedaService();
            try {
                List<Moeda> lista = service.buscarMoedas();
                if (StringUtils.containsLetter(value)) {
                    for (Moeda moeda : lista) {
                        if (moeda.getNome().equals(value)) {
                            return new MoedaBV(moeda);
                        }
                    }
                } else {
                    for (Moeda moeda : lista) {
                        if (moeda.getId().equals(new Long(value))) {
                            return new MoedaBV(moeda);
                        }
                    }
                }
                return new MoedaBV();
            } catch (Exception e) {
                return new MoedaBV();
            }
        } else {
            return new MoedaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((MoedaBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
