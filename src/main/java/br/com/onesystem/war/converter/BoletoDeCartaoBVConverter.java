/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.war.service.BoletoDeCartaoService;
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
@FacesConverter(value = "boletoDeCartaoBVConverter", forClass = BoletoDeCartao.class)
public class BoletoDeCartaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof BoletoDeCartao) {
                return new BoletoDeCartaoBV((BoletoDeCartao) object);
            } else if (object instanceof BoletoDeCartaoBV) {
                return (BoletoDeCartaoBV) object;
            }
        }
        return new BoletoDeCartaoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof BoletoDeCartaoBV) {
                String id = String.valueOf(((BoletoDeCartaoBV) object).getId());
                uic.getAttributes().put(id, (BoletoDeCartaoBV) object);
                return id;
            } else if (object instanceof BoletoDeCartao) {
                String id = String.valueOf(((BoletoDeCartao) object).getId());
                uic.getAttributes().put(id, (BoletoDeCartao) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
