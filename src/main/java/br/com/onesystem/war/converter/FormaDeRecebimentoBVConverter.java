/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.FormaDeRecebimentoBV;
import br.com.onesystem.war.service.FormaDeRecebimentoService;
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
@FacesConverter(value = "formaDeRecebimentoBVConverter", forClass = FormaDeRecebimento.class)
public class FormaDeRecebimentoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<FormaDeRecebimento> lista = new FormaDeRecebimentoService().buscarFormasDeRecebimento();
                if (StringUtils.containsLetter(value)) {
                    for (FormaDeRecebimento formaDeRecebimento : lista) {
                        if (formaDeRecebimento.getNome().equals(value)) {
                            return new FormaDeRecebimentoBV(formaDeRecebimento);
                        }
                    }
                } else {
                    for (FormaDeRecebimento formaDeRecebimento : lista) {
                        if (formaDeRecebimento.getId().equals(new Long(value))) {
                            return new FormaDeRecebimentoBV(formaDeRecebimento);
                        }
                    }
                }
                return new FormaDeRecebimentoBV();
            } catch (Exception e) {
                return new FormaDeRecebimentoBV();
            }
        } else {
            return new FormaDeRecebimentoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((FormaDeRecebimentoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
