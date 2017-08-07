/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.TipoDespesaService;
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
@FacesConverter(value = "tipoDespesaConverter", forClass = TipoDespesa.class)
public class TipoDespesaConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<TipoDespesa> lista = new TipoDespesaService().buscarTiposDeDespesa();
                if (StringUtils.containsLetter(value)) {
                    for (TipoDespesa despesa : lista) {
                        if (despesa.getNome().equals(value)) {
                            return despesa;
                        }
                    }
                } else {
                    for (TipoDespesa despesa : lista) {
                        if (despesa.getId().equals(new Long(value))) {
                            return despesa;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma despesa válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((TipoDespesa) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
