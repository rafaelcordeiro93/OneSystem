/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cep;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.CepBV;
import br.com.onesystem.war.service.CepService;
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
@FacesConverter(value = "cepBVConverter", forClass = CepBV.class)
public class CepBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Cep> lista = new CepService().buscarCeps();
                if (StringUtils.containsLetter(value)) {
                    for (Cep cep : lista) {
                        if (cep.getCep().equals(value)) {
                            return new CepBV(cep);
                        }
                    }
                } else {
                    for (Cep cep : lista) {
                        if (cep.getId().equals(new Long(value))) {
                            return new CepBV(cep);
                        }
                    }
                }
                return new CepBV();
            } catch (Exception e) {
                return new CepBV();
            }
        } else {
            return new CepBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((CepBV) object).getCep());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
