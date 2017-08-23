/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.war.builder.CotacaoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "cotacaoBVConverter", forClass = CotacaoBV.class)
public class CotacaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Cotacao) {
                return new CotacaoBV((Cotacao) object);
            } else if (object instanceof CotacaoBV) {
                return (CotacaoBV) object;
            }
        }
        return new CotacaoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof CotacaoBV) {
                String id = String.valueOf(((CotacaoBV) object).getId());
                uic.getAttributes().put(id, (CotacaoBV) object);
                return id;
            } else if (object instanceof Cotacao) {
                String id = String.valueOf(((Cotacao) object).getId());
                uic.getAttributes().put(id, (Cotacao) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
