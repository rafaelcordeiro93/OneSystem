/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
import br.com.onesystem.war.service.AjusteDeEstoqueService;
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
@FacesConverter(value = "ajusteDeEstoqueBVConverter", forClass = AjusteDeEstoque.class)
public class AjusteDeEstoqueBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                AjusteDeEstoqueService service = new AjusteDeEstoqueService();
                List<AjusteDeEstoque> lista = service.buscarAjusteDeEstoques();
                for (AjusteDeEstoque ajusteDeEstoque : lista) {
                    if (ajusteDeEstoque.getId().equals(new Long(value))) {
                        return new AjusteDeEstoqueBV(ajusteDeEstoque);
                    }
                }
                return new AjusteDeEstoqueBV();
            } catch (Exception e) {
                return new AjusteDeEstoqueBV();
            }
        } else {
            return new AjusteDeEstoqueBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((AjusteDeEstoqueBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
