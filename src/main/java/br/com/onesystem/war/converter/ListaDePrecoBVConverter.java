/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.ListaDePrecoBV;
import br.com.onesystem.war.service.ListaDePrecoService;
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
@FacesConverter(value = "listaDePrecoBVConverter", forClass = ListaDePrecoBV.class)
public class ListaDePrecoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<ListaDePreco> lista = new ListaDePrecoService().buscarListaPrecos();
                if (StringUtils.containsLetter(value)) {
                    for (ListaDePreco listaDePreco : lista) {
                        if (listaDePreco.getNome().equals(value)) {
                            return new ListaDePrecoBV(listaDePreco);
                        }
                    }
                } else {
                    for (ListaDePreco listaDePreco : lista) {
                        if (listaDePreco.getId().equals(new Long(value))) {
                            return new ListaDePrecoBV(listaDePreco);
                        }
                    }
                }
                return new ListaDePrecoBV();
            } catch (Exception e) {
                return new ListaDePrecoBV();
            }
        } else {
            return new ListaDePrecoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ListaDePrecoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
