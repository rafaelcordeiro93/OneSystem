/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.ContaDeEstoqueBV;
import br.com.onesystem.war.service.ContaDeEstoqueService;
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
@FacesConverter(value = "contaDeEstoqueBVConverter", forClass = ContaDeEstoque.class)
public class ContaDeEstoqueBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<ContaDeEstoque> lista = new ContaDeEstoqueService().buscarContaDeEstoque();
                if (StringUtils.containsLetter(value)) {
                    for (ContaDeEstoque contaDeEstoqueBV : lista) {
                        if (contaDeEstoqueBV.getNome().equals(value)) {
                            return new ContaDeEstoqueBV(contaDeEstoqueBV);
                        }
                    }
                } else {
                    for (ContaDeEstoque contaDeEstoqueBV : lista) {
                        if (contaDeEstoqueBV.getId().equals(new Long(value))) {
                            return new ContaDeEstoqueBV(contaDeEstoqueBV);
                        }
                    }
                }
                return new ContaDeEstoqueBV();
            } catch (Exception e) {
                return new ContaDeEstoqueBV();
            }
        } else {
            return new ContaDeEstoqueBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ContaDeEstoqueBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
