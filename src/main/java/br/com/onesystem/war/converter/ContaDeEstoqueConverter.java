/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.ContaDeEstoqueService;
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
@FacesConverter(value = "contaDeEstoqueConverter", forClass = ContaDeEstoque.class)
public class ContaDeEstoqueConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                ContaDeEstoqueService service = (ContaDeEstoqueService) fc.getExternalContext().getApplicationMap().get("contaDeEstoqueService");
                List<ContaDeEstoque> lista = service.buscarContaDeEstoque();
                if (StringUtils.containsLetter(value)) {
                    for (ContaDeEstoque contaDeEstoque : lista) {
                        if (contaDeEstoque.getNome().equals(value)) {
                            return contaDeEstoque;
                        }
                    }
                } else {
                    for (ContaDeEstoque contaDeEstoque : lista) {
                        if (contaDeEstoque.getId().equals(new Long(value))) {
                            return contaDeEstoque;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é uma contaDeEstoque válida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ContaDeEstoque) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
