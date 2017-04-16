/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.IVA;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.IVABV;
import br.com.onesystem.war.service.IVAService;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "ivaBVConverter", forClass = IVABV.class)
public class IVABVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<IVA> lista = new IVAService().buscarIVAs();
                if (!StringUtils.containsLetter(value)) {
                    for (IVA iva : lista) {
                        if (iva.getId().equals(new Long(value))) {
                            return new IVABV(iva);
                        }
                    }
                }
                return new IVABV();
            } catch (Exception e) {
                return new IVABV();
            }
        } else {
            return new IVABV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((IVABV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
