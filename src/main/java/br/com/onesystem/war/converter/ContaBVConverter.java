/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.ContaBV;
import br.com.onesystem.war.service.ContaService;
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
@FacesConverter(value = "contaBVConverter", forClass = ContaBV.class)
public class ContaBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                ContaService service = (ContaService) fc.getExternalContext().getApplicationMap().get("contaService");
                List<Conta> lista = service.buscarContas();
                if (StringUtils.containsLetter(value)) {
                    for (Conta conta : lista) {
                        if (conta.getNome().equals(value)) {
                            return new ContaBV(conta);
                        }
                    }
                } else {
                    for (Conta conta : lista) {
                        if (conta.getId().equals(new Long(value))) {
                            return new ContaBV(conta);
                        }
                    }
                }
                return new ContaBV();
            } catch (Exception e) {
                return new ContaBV();
            }
        } else {
            return new ContaBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ContaBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
