/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.war.service.ReceitaProvisionadaService;
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
@FacesConverter(value = "receitaProvisionadaConverter", forClass = ReceitaProvisionada.class)
public class ReceitaProvisionadaConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                ReceitaProvisionada encontrado = new ArmazemDeRegistros<>(ReceitaProvisionada.class).find(new Long(value));
                if (encontrado != null) {
                    return encontrado;
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma receitaProvisionada válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ReceitaProvisionada) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
