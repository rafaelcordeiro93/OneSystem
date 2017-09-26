/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.LoteNotaFiscal;
import br.com.onesystem.war.builder.LoteNotaFiscalBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael Cordeiro
 */
@FacesConverter(value = "loteNotaFiscalBVConverter", forClass = LoteNotaFiscalBV.class)
public class LoteNotaFiscalBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof LoteNotaFiscal) {
                return new LoteNotaFiscalBV((LoteNotaFiscal) object);
            } else if (object instanceof LoteNotaFiscalBV) {
                return (LoteNotaFiscalBV) object;
            }
        }
        return new LoteNotaFiscalBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof LoteNotaFiscalBV) {
                String id = String.valueOf(((LoteNotaFiscalBV) object).getId());
                uic.getAttributes().put(id, (LoteNotaFiscalBV) object);
                return id;
            } else if (object instanceof LoteNotaFiscal) {
                String id = String.valueOf(((LoteNotaFiscal) object).getId());
                uic.getAttributes().put(id, (LoteNotaFiscal) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
