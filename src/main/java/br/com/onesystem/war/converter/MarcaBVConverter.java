/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Marca;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.MarcaBV;
import br.com.onesystem.war.service.MarcaService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "marcaBVConverter", forClass = MarcaBV.class)
public class MarcaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Marca> lista = new MarcaService().buscarMarcas();
                if (StringUtils.containsLetter(value)) {
                    for (Marca marca : lista) {
                        if (marca.getNome().equals(value)) {
                            return new MarcaBV(marca);
                        }
                    }
                } else {
                    for (Marca marca : lista) {
                        if (marca.getId().equals(new Long(value))) {
                            return new MarcaBV(marca);
                        }
                    }
                }
                return new MarcaBV();
            } catch (Exception e) {
                return new MarcaBV();
            }
        } else {
            return new MarcaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((MarcaBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
