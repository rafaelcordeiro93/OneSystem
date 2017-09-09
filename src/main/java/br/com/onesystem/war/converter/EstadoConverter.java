/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.EstadoService;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "estadoConverter", forClass = Estado.class)
public class EstadoConverter implements Converter, Serializable {

    @Inject
    private EstadoService estadoService;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Estado> lista = estadoService.buscarEstados();
                if (StringUtils.containsLetter(value)) {
                    for (Estado estado : lista) {
                        if (estado.getNome().equals(value)) {
                            return estado;
                        }
                    }
                } else {
                    for (Estado estado : lista) {
                        if (estado.getId().equals(new Long(value))) {
                            return estado;
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
                return String.valueOf(((Estado) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
