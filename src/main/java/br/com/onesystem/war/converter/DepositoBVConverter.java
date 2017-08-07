/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Deposito;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.DepositoBV;
import br.com.onesystem.war.service.DepositoService;
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
@FacesConverter(value = "depositoBVConverter", forClass = Deposito.class)
public class DepositoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Deposito> lista = new DepositoService().buscarDepositos();
                if (StringUtils.containsLetter(value)) {
                    for (Deposito deposito : lista) {
                        if (deposito.getNome().equals(value)) {
                            return new DepositoBV(deposito);
                        }
                    }
                } else {
                    for (Deposito deposito : lista) {
                        if (deposito.getId().equals(new Long(value))) {
                            return new DepositoBV(deposito);
                        }
                    }
                }
                return new DepositoBV();
            } catch (Exception e) {
                return new DepositoBV();
            }
        } else {
            return new DepositoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((DepositoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
