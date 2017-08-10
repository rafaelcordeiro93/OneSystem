/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Pais;
import br.com.onesystem.domain.Pais;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.PaisService;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "paisConverter", forClass = Pais.class)
public class PaisConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Pais> lista = new PaisService().buscarPais();
                if (StringUtils.containsLetter(value)) {
                    for (Pais pais : lista) {
                        if (pais.getNome().equals(value)) {
                            return pais;
                        }
                    }
                } else {
                    for (Pais pais : lista) {
                        if (pais.getId().equals(new Long(value))) {
                            return pais;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma receita válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((Pais) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
