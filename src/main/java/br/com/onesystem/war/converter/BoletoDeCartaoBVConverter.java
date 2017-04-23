/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.util.StringUtils;
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
        if (value != null && value.trim().length() > 0) {
            try {
                BoletoDeCartaoService service = new BoletoDeCartaoService();
                List<BoletoDeCartao> lista = service.buscarBoletoDeCartaos();
                if (!StringUtils.containsLetter(value)) {
                    for (BoletoDeCartao boletoDeCartao : lista) {
                        if (boletoDeCartao.getId().equals(new Long(value))) {
                            return new BoletoDeCartaoBV(boletoDeCartao);
                        }
                    }
                }
                return null;
            } catch (NumberFormatException e) {
                return new BoletoDeCartaoBV();
            }
        } else {
            return new BoletoDeCartaoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((BoletoDeCartaoBV) object).getId());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return null;
        }
    }
}
