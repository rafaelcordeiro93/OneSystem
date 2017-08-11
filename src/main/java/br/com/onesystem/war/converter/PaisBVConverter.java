/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.PaisBV;
import br.com.onesystem.war.service.PaisService;
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
@FacesConverter(value = "paisBVConverter", forClass = PaisBV.class)
public class PaisBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Pais> lista = new PaisService().buscarPais();
                if (StringUtils.containsLetter(value)) {
                    for (Pais pais : lista) {
                        if (pais.getNome().equals(value)) {
                            return new PaisBV(pais);
                        }
                    }
                } else {
                    for (Pais pais : lista) {
                        if (pais.getId().equals(new Long(value))) {
                            return new PaisBV(pais);
                        }
                    }
                }
                return new PaisBV();
            } catch (Exception e) {
                return new PaisBV();
            }
        } else {
            return new PaisBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((PaisBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
