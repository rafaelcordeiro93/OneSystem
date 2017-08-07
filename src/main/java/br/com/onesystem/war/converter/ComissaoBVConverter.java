/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.ComissaoBV;
import br.com.onesystem.war.service.ComissaoService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "comissaoBVConverter", forClass = ComissaoBV.class)
public class ComissaoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Comissao> lista = new ComissaoService().buscarComissao();
                if (StringUtils.containsLetter(value)) {
                    for (Comissao comissao : lista) {
                        if (comissao.getNome().equals(value)) {
                            return new ComissaoBV(comissao);
                        }
                    }
                } else {
                    for (Comissao comissao : lista) {
                        if (comissao.getId().equals(new Long(value))) {
                            return new ComissaoBV(comissao);
                        }
                    }
                }
                return new ComissaoBV();
            } catch (Exception e) {
                return new ComissaoBV();
            }
        } else {
            return new ComissaoBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((ComissaoBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
