/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.BancoService;
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
@FacesConverter(value = "bancoConverter", forClass = Banco.class)
public class BancoConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                BancoService service = (BancoService) fc.getExternalContext().getApplicationMap().get("bancoService");
                List<Banco> lista = service.buscarBancos();
                if (StringUtils.containsLetter(value)) {
                    for (Banco banco : lista) {
                        if (banco.getNome().equals(value)) {
                            return banco;
                        }
                    }
                } else {
                    for (Banco banco : lista) {
                        if (banco.getId().equals(new Long(value))) {
                            return banco;
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Não é um banco válido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((Banco) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
