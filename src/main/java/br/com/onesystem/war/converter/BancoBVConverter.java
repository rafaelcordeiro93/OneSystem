/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.BancoBV;
import br.com.onesystem.war.service.BancoService;
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
@FacesConverter(value = "bancoBVConverter", forClass = BancoBV.class)
public class BancoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Banco> lista = new BancoService().buscarBancos();
                if (StringUtils.containsLetter(value)) {
                    for (Banco banco : lista) {
                        if (banco.getNome().equals(value)) {
                            return new BancoBV(banco);
                        }
                    }
                } else {
                    for (Banco banco : lista) {
                        if (banco.getId().equals(new Long(value))) {
                            return new BancoBV(banco);
                        }
                    }
                }
                return new BancoBV();
            } catch (Exception e) {
                return new BancoBV();
            }
        } else {
            return new BancoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((BancoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
