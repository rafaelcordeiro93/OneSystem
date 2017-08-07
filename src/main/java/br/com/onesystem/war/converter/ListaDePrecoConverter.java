/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ListaDePrecoService;
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
@FacesConverter(value = "listaDePrecoConverter", forClass = ListaDePreco.class)
public class ListaDePrecoConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<ListaDePreco> lista = new ListaDePrecoService().buscarListaPrecos();
                if (StringUtils.containsLetter(value)) {
                    for (ListaDePreco listaDePreco : lista) {
                        if (listaDePreco.getNome().equals(value)) {
                            return listaDePreco;
                        }
                    }
                } else {
                    for (ListaDePreco listaDePreco : lista) {
                        if (listaDePreco.getId().equals(new Long(value))) {
                            return listaDePreco;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma listaDePreco válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ListaDePreco) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
