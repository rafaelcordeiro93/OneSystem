/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.CartaoBV;
import br.com.onesystem.war.service.CartaoService;
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
@FacesConverter(value = "cartaoBVConverter", forClass = CartaoBV.class)
public class CartaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Cartao> lista = new CartaoService().buscarCartaos();
                if (StringUtils.containsLetter(value)) {
                    for (Cartao cartao : lista) {
                        if (cartao.getNome().equals(value)) {
                            return new CartaoBV(cartao);
                        }
                    }
                } else {
                    for (Cartao cartao : lista) {
                        if (cartao.getId().equals(new Long(value))) {
                            return new CartaoBV(cartao);
                        }
                    }
                }
                return new CartaoBV();
            } catch (Exception e) {
                return new CartaoBV();
            }
        } else {
            return new CartaoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((CartaoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
