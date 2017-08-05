/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.war.builder.ConhecimentoDeFreteBV;
import br.com.onesystem.war.service.ConhecimentoDeFreteService;
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
@FacesConverter(value = "conhecimentoDeFreteBVConverter", forClass = ConhecimentoDeFrete.class)
public class ConhecimentoDeFreteBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<ConhecimentoDeFrete> lista = new ConhecimentoDeFreteService().buscarConhecimentoDeFrete();
                for (ConhecimentoDeFrete conhecimentoDeFreteBV : lista) {
                    if (conhecimentoDeFreteBV.getId().equals(new Long(value))) {
                        return new ConhecimentoDeFreteBV(conhecimentoDeFreteBV);
                    }
                }
                return new ConhecimentoDeFreteBV();
            } catch (Exception e) {
                return new ConhecimentoDeFreteBV();
            }
        } else {
            return new ConhecimentoDeFreteBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ConhecimentoDeFreteBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
