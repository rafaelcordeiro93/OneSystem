/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
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
@FacesConverter(value = "aJusteDeEstoqueConverter", forClass = AjusteDeEstoque.class)
public class AJusteDeEstoqueConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                AjusteDeEstoqueService service = new AjusteDeEstoqueService();
                List<AjusteDeEstoque> lista = service.buscarAjusteDeEstoques();
                if (!StringUtils.containsLetter(value)) {
                    for (AjusteDeEstoque ajusteDeEstoque : lista) {
                        if (ajusteDeEstoque.getId().equals(new Long(value))) {
                            return ajusteDeEstoque;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um ajuste de estoque válido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((AjusteDeEstoque) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
