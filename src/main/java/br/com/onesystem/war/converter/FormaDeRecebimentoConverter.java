/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.FormaDeRecebimentoService;
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
@FacesConverter(value = "formaDeRecebimentoConverter", forClass = FormaDeRecebimento.class)
public class FormaDeRecebimentoConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                FormaDeRecebimentoService service = (FormaDeRecebimentoService) fc.getExternalContext().getApplicationMap().get("formaDeRecebimentoService");
                List<FormaDeRecebimento> lista = service.buscarFormasDeRecebimento();
                if (StringUtils.containsLetter(value)) {
                    for (FormaDeRecebimento formaDeRecebimento : lista) {
                        if (formaDeRecebimento.getNome().equals(value)) {
                            return formaDeRecebimento;
                        }
                    }
                } else {
                    for (FormaDeRecebimento formaDeRecebimento : lista) {
                        if (formaDeRecebimento.getId().equals(new Long(value))) {
                            return formaDeRecebimento;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma formaDeRecebimento válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((FormaDeRecebimento) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
