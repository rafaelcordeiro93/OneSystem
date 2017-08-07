/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.UnidadeMedidaItemService;
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
@FacesConverter(value = "unidadeMedidaItemConverter", forClass = UnidadeMedidaItem.class)
public class UnidadeMedidaItemConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<UnidadeMedidaItem> lista = new UnidadeMedidaItemService().buscarUnidadeMedidaItens();
                if (StringUtils.containsLetter(value)) {
                    for (UnidadeMedidaItem unidadeMedidaItem : lista) {
                        if (unidadeMedidaItem.getNome().equals(value)) {
                            return unidadeMedidaItem;
                        }
                    }
                } else {
                    for (UnidadeMedidaItem unidadeMedidaItem : lista) {
                        if (unidadeMedidaItem.getId().equals(new Long(value))) {
                            return unidadeMedidaItem;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma unidadeMedidaItem válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((UnidadeMedidaItem) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
