/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Cfop;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.builder.CfopBV;
import br.com.onesystem.war.service.CfopService;
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
@FacesConverter(value = "cfopBVConverter", forClass = CfopBV.class)
public class CfopBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                List<Cfop> lista = new CfopService().buscarCfops();
                if (StringUtils.containsLetter(value)) {
                    for (Cfop cfop : lista) {
                        if (cfop.getNome().equals(value)) {
                            return new CfopBV(cfop);
                        }
                    }
                } else {
                    for (Cfop cfop : lista) {
                        if (cfop.getId().equals(new Long(value))) {
                            System.out.println("Cfop: " + cfop);
                            return new CfopBV(cfop);
                        }
                    }
                }
                return new CfopBV();
            } catch (Exception e) {
                return new CfopBV();
            }
        } else {
            return new CfopBV();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            try {
                return String.valueOf(((CfopBV) object).getNome());
            } catch (ClassCastException cce) {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
